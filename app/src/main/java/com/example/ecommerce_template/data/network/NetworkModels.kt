package com.example.ecommerce_template.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class TokenResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String,
    @SerialName("refresh_token") val refreshToken: String? = null
)

@Serializable
data class ProductResponse(
    val id: String,
    val name: String,
    val description: String? = null,
    val price: Double,
    val stock: Int,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("category_id") val categoryId: String,
    @SerialName("category_name") val categoryName: String
)

@Serializable
data class PaginatedProductResponse(
    val items: List<ProductResponse>,
    val page: Int,
    val size: Int,
    val total: Int,
    val pages: Int
)
