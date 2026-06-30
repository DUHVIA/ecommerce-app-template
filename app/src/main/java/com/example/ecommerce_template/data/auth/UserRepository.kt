package com.example.ecommerce_template.data.auth

import com.example.ecommerce_template.data.network.IronCoreApiService
import com.example.ecommerce_template.data.network.LoginRequest
import com.example.ecommerce_template.data.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserRepository(
    private val apiService: IronCoreApiService,
    private val tokenManager: TokenManager
) {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!.accessToken
                tokenManager.saveToken(token)
                
                // Set un usuario temporal
                _currentUser.value = User(id = "1", name = "API User", email = email, password = "")
                Result.success(true)
            } else {
                Result.failure(Exception("Error de credenciales"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun register(name: String, email: String, password: String): Result<Boolean> {
        // Podríamos invocar apiService.register(..) si tuviéramos el endpoint mapeado
        return Result.success(true)
    }

    suspend fun logout() {
        tokenManager.clearToken()
        _currentUser.value = null
    }

    fun getCurrentUserProfile(): UserPublicProfile? {
        return _currentUser.value?.let { user ->
            UserPublicProfile(
                name = user.name,
                email = user.email
            )
        }
    }
}