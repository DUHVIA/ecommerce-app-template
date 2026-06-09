package com.example.ecommerce_template.data.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object UserRepository {

    // 1. Lista de todos los usuarios registrados
    private val _registeredUsers = mutableStateListOf<User>()

    // 2. Estado del usuario actual (quien ha iniciado sesión)
    private var _currentUser by mutableStateOf<User?>(null)

    val currentUser: User? get() = _currentUser

    init {
        _registeredUsers.add(
            User(
                id = "DEFAULT-001",
                name = "Gabriel Soto",
                email = "admin",
                password = "admin"
            )
        )
    }

    // Registro: Agrega un usuario a la lista y lo loguea automáticamente
    fun register(name: String, email: String, password: String) {
        val newUser = User(
            id = "USR-${System.currentTimeMillis()}",
            name = name,
            email = email,
            password = password
        )
        _registeredUsers.add(newUser)
        _currentUser = newUser
    }

    // Login: Busca en la lista de registrados
    fun login(email: String, password: String): Boolean {
        val user = _registeredUsers.find { it.email == email && it.password == password }
        if (user != null) {
            _currentUser = user
            return true
        }
        return false
    }

    // Logout
    fun logout() {
        _currentUser = null
    }

    fun getCurrentUserProfile(): UserPublicProfile? {
        return _currentUser?.let {
            UserPublicProfile(
                name = it.name,
                email = it.email
            )
        }
    }
}