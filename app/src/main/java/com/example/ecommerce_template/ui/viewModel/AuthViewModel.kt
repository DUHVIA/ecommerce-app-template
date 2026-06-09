package com.example.ecommerce_template.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel

import com.example.ecommerce_template.data.auth.UserRepository

class AuthViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var loginError by mutableStateOf<String?>(null)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
        if (loginError != null) loginError = null
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        if (loginError != null) loginError = null
    }

    fun login(onSuccess: () -> Unit) {
        val success = UserRepository.login(email, password)
        if (success) {
            onSuccess()
        } else {
            loginError = "Credenciales incorrectas"
        }
    }
}