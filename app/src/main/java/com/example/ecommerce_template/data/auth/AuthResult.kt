package com.example.ecommerce_template.data.auth

sealed interface AuthResult {
    // Estado cuando el login o registro es correcto, contiene los datos del usuario
    data class Success(val user: AuthUser) : AuthResult

    // Estado cuando algo falla (contraseña incorrecta, campos vacíos, etc.)
    data class Error(val message: String) : AuthResult
}