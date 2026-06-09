package com.example.ecommerce_template.data.auth

class AuthRepository {

    // Usuario de gimnasio pre-registrado para probar el Login directo
    private val testEmail = "gabriel.soto@email.com"
    private val testPassword = "password123"

    // 1. Simulación de Login
    fun login(email: String, pass: String): AuthResult {
        return if (email == testEmail && pass == testPassword) {
            AuthResult.Success(
                AuthUser(
                    id = "usr_001", // Este ID coincide con el del perfil
                    email = testEmail,
                    username = "gabrielsoto",
                    token = "mock_token_xyz"
                )
            )
        } else {
            AuthResult.Error("Credenciales incorrectas. Prueba con el usuario de prueba.")
        }
    }

    // 2. Simulación de Registro
    fun register(email: String, username: String, pass: String): AuthResult {
        return if (email.isBlank() || username.isBlank() || pass.isBlank()) {
            AuthResult.Error("Todos los campos son obligatorios.")
        } else {
            AuthResult.Success(
                AuthUser(id = "usr_001", email = email, username = username, token = "mock_token_xyz")
            )
        }
    }
}