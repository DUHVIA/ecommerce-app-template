package com.example.ecommerce_template.ui.viewModel


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import com.example.ecommerce_template.data.auth.UserRepository

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loginError: String? = null,
    val isLoading: Boolean = false
)

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val isLoading: Boolean = false
)

class AuthViewModel : ViewModel() {

    // LOGIN

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState.asStateFlow()

    // REGISTER

    private val _registerState = MutableStateFlow(RegisterUiState())
    val registerState: StateFlow<RegisterUiState> = _registerState.asStateFlow()

    // PROFILE

    private val _profileState = MutableStateFlow(ProfileUiState())
    val profileState: StateFlow<ProfileUiState> = _profileState.asStateFlow()

    init {
        viewModelScope.launch {
            UserRepository.currentUser.collect { user ->

                _profileState.value = ProfileUiState(
                    name = user?.name ?: "Usuario Desconocido",
                    email = user?.email ?: "",
                    isLoading = false
                )

            }
        }
    }

    // LOGIN (Operaciones)
    fun onEmailChange(newEmail: String) {
        _loginState.update {
            it.copy(email = newEmail, loginError = null)
        }
    }

    fun onPasswordChange(newPassword: String) {
        _loginState.update {
            it.copy(password = newPassword, loginError = null)
        }
    }

    fun login(onSuccess: () -> Unit) {
        val currentState = _loginState.value

        _loginState.update {
            it.copy(isLoading = true, loginError = null)
        }

        viewModelScope.launch {
            //delay(2000)

            val success = UserRepository.login(
                currentState.email,
                currentState.password
            )

            _loginState.update {
                if (success) {
                    it.copy(isLoading = false)
                } else {
                    it.copy(
                        isLoading = false,
                        loginError = "Credenciales incorrectas"
                    )
                }
            }

            if (success) {
                onSuccess()
            }
        }
    }

    // REGISTER (Operaciones)
    fun onRegisterNameChange(newName: String) {
        _registerState.update {
            it.copy(name = newName, errorMessage = null)
        }
    }

    fun onRegisterEmailChange(newEmail: String) {
        _registerState.update {
            it.copy(email = newEmail, errorMessage = null)
        }
    }

    fun onRegisterPasswordChange(newPassword: String) {
        _registerState.update {
            it.copy(password = newPassword, errorMessage = null)
        }
    }

    fun onRegisterConfirmPasswordChange(newConfirm: String) {
        _registerState.update {
            it.copy(confirmPassword = newConfirm, errorMessage = null)
        }
    }

    fun register(onSuccess: () -> Unit) {

        val currentState = _registerState.value

        if (
            currentState.name.isBlank() ||
            currentState.email.isBlank() ||
            currentState.password.isBlank()
        ) {
            _registerState.update {
                it.copy(errorMessage = "Todos los campos son obligatorios")
            }
            return
        }

        if (currentState.password != currentState.confirmPassword) {
            _registerState.update {
                it.copy(errorMessage = "Las contraseñas no coinciden")
            }
            return
        }

        UserRepository.register(
            currentState.name,
            currentState.email,
            currentState.password
        )

        onSuccess()
    }

    // PROFILE (Operaciones)
    fun logout(onLogoutSuccess: () -> Unit) {

        UserRepository.logout()

        onLogoutSuccess()
    }
}