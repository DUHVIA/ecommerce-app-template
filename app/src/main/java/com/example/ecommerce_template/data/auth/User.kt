package com.example.ecommerce_template.data.auth

data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String
)

data class UserPublicProfile(
    val name: String,
    val email: String
)