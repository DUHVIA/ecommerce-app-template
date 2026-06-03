package com.example.ecommerce_template.data.profile

data class Profile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val shippingAddress: String,
    val city: String,
    val avatarUrl: String? = null
)
