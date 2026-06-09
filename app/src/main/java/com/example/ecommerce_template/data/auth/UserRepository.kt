package com.example.ecommerce_template.data.auth

import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object UserRepository {

    private val _registeredUsers = mutableStateListOf<User>()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        _registeredUsers.add(
            User(
                id = "DEFAULT-001",
                name = "Gabriel Soto",
                email = "admin",
                password = "admin"
            )
        )
        _registeredUsers.add(
            User(
                id = "DEFAULT-002",
                name = "Diego Nina",
                email = "dninas",
                password = "admin"
            )
        )
    }

    fun register(name: String, email: String, password: String) {
        val newUser = User(
            id = "USR-${System.currentTimeMillis()}",
            name = name,
            email = email,
            password = password
        )

        _registeredUsers.add(newUser)
        _currentUser.value = newUser
    }

    fun login(email: String, password: String): Boolean {
        val user = _registeredUsers.find {
            it.email == email && it.password == password
        }

        return if (user != null) {
            _currentUser.value = user
            true
        } else {
            false
        }
    }

    fun logout() {
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