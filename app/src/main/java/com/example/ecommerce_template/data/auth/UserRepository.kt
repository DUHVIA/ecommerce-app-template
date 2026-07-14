package com.example.ecommerce_template.data.auth

import com.example.ecommerce_template.core.ApiResult
import com.example.ecommerce_template.core.safeCall
import com.example.ecommerce_template.network.api.AuthApi
import com.example.ecommerce_template.network.api.UserApi
import com.example.ecommerce_template.network.dto.auth.LoginRequestDto
import com.example.ecommerce_template.network.dto.auth.RegisterRequestDto
import com.example.ecommerce_template.network.dto.user.PasswordUpdateDto
import com.example.ecommerce_template.network.dto.user.ProfileUpsertDto
import com.example.ecommerce_template.network.dto.user.UserUpdateDto
import com.example.ecommerce_template.network.mapper.toDomain
import com.example.ecommerce_template.data.profile.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserRepository(
    private val authApi: AuthApi,
    private val userApi: UserApi,
    private val tokenStore: TokenStore
) {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    suspend fun login(email: String, password: String): ApiResult<User> {
        val result = safeCall { authApi.login(LoginRequestDto(email = email, password = password)) }
        return when (result) {
            is ApiResult.Success -> {
                tokenStore.save(result.data.accessToken, result.data.refreshToken)
                refreshMe()
            }
            is ApiResult.Error -> result
            ApiResult.Loading -> ApiResult.Loading
        }
    }

    suspend fun register(name: String, email: String, password: String): ApiResult<User> {
        val result = safeCall {
            authApi.register(RegisterRequestDto(name = name, email = email, password = password))
        }
        return when (result) {
            is ApiResult.Success -> {
                login(email, password)
            }
            is ApiResult.Error -> result
            ApiResult.Loading -> ApiResult.Loading
        }
    }

    suspend fun refreshMe(): ApiResult<User> {
        val result = safeCall { userApi.me() }
        if (result is ApiResult.Success) {
            _currentUser.value = result.data.toDomain()
        } else if (result is ApiResult.Error && result.httpStatus == 401) {
            tokenStore.clear()
            _currentUser.value = null
        }
        return result.map { it.toDomain() }
    }

    suspend fun updateMe(name: String?, email: String?): ApiResult<User> {
        val result = safeCall { userApi.updateMe(UserUpdateDto(name = name, email = email)) }
        if (result is ApiResult.Success) {
            _currentUser.value = result.data.toDomain()
        }
        return result.map { it.toDomain() }
    }

    suspend fun changePassword(currentPassword: String, newPassword: String): ApiResult<Unit> {
        return safeCall {
            userApi.changePassword(
                PasswordUpdateDto(currentPassword = currentPassword, newPassword = newPassword)
            )
            Unit
        }
    }

    suspend fun upsertProfile(profile: ProfileUpsertDto): ApiResult<Profile> {
        val result = safeCall { userApi.upsertProfile(profile) }
        return result.map { it.toDomain() }
    }

    suspend fun logout() {
        tokenStore.clear()
        _currentUser.value = null
    }
}
