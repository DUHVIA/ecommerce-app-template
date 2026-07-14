package com.example.ecommerce_template.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_template.data.auth.UserRepository
import com.example.ecommerce_template.core.ApiResult
import com.example.ecommerce_template.core.errorMessageOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow(RegisterUiState())
    val registerState: StateFlow<RegisterUiState> = _registerState.asStateFlow()

    private val _profileState = MutableStateFlow(ProfileUiState())
    val profileState: StateFlow<ProfileUiState> = _profileState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.currentUser.collect { user ->
                _profileState.value = ProfileUiState(
                    name = user?.name ?: "Invitado",
                    email = user?.email.orEmpty(),
                    isLoading = false,
                    isLoggedIn = user != null
                )
            }
        }
        refreshSession()
    }

    fun refreshSession() {
        viewModelScope.launch {
            _profileState.update { it.copy(isLoading = true) }
            userRepository.refreshMe()
            _profileState.update { it.copy(isLoading = false) }
        }
    }

    fun onEmailChange(newEmail: String) {
        _loginState.update { it.copy(email = newEmail, loginError = null) }
    }

    fun onPasswordChange(newPassword: String) {
        _loginState.update { it.copy(password = newPassword, loginError = null) }
    }

    fun login(onSuccess: () -> Unit) {
        val current = _loginState.value
        _loginState.update { it.copy(isLoading = true, loginError = null) }
        viewModelScope.launch {
            val result = userRepository.login(current.email, current.password)
            when (result) {
                is ApiResult.Success -> {
                    _loginState.update { it.copy(isLoading = false) }
                    onSuccess()
                }
                is ApiResult.Error -> {
                    _loginState.update {
                        it.copy(isLoading = false, loginError = result.errorMessageOrNull())
                    }
                }
                ApiResult.Loading -> Unit
            }
        }
    }

    fun onRegisterNameChange(v: String) {
        _registerState.update { it.copy(name = v, errorMessage = null) }
    }

    fun onRegisterEmailChange(v: String) {
        _registerState.update { it.copy(email = v, errorMessage = null) }
    }

    fun onRegisterPasswordChange(v: String) {
        _registerState.update { it.copy(password = v, errorMessage = null) }
    }

    fun onRegisterConfirmPasswordChange(v: String) {
        _registerState.update { it.copy(confirmPassword = v, errorMessage = null) }
    }

    fun register(onSuccess: () -> Unit) {
        val current = _registerState.value
        if (current.name.isBlank() || current.email.isBlank() || current.password.isBlank()) {
            _registerState.update { it.copy(errorMessage = "Todos los campos son obligatorios") }
            return
        }
        if (current.password != current.confirmPassword) {
            _registerState.update { it.copy(errorMessage = "Las contraseñas no coinciden") }
            return
        }
        _registerState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            val result = userRepository.register(current.name, current.email, current.password)
            when (result) {
                is ApiResult.Success -> {
                    _registerState.update { it.copy(isLoading = false) }
                    onSuccess()
                }
                is ApiResult.Error -> {
                    _registerState.update {
                        it.copy(isLoading = false, errorMessage = result.errorMessageOrNull())
                    }
                }
                ApiResult.Loading -> Unit
            }
        }
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            _profileState.update { it.copy(isLoading = true) }
            userRepository.logout()
            _profileState.update { ProfileUiState() }
            onLogoutSuccess()
        }
    }
}
