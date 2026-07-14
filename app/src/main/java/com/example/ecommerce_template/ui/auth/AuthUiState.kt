package com.example.ecommerce_template.ui.auth

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
    val name: String = "Invitado",
    val email: String = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false
)
