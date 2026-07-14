package com.example.ecommerce_template.network.dto.cart

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartItemCreateDto(
    @SerialName("product_id") val productId: String,
    val quantity: Int = 1
)

@Serializable
data class CartItemUpdateDto(
    val quantity: Int
)

@Serializable
data class CartItemDto(
    val id: String,
    @SerialName("product_id") val productId: String,
    @SerialName("product_name") val productName: String,
    @SerialName("product_image_url") val productImageUrl: String? = null,
    @SerialName("unit_price") val unitPrice: Double,
    val quantity: Int,
    val subtotal: Double,
    @SerialName("added_at") val addedAt: String
)

@Serializable
data class CartDto(
    val items: List<CartItemDto> = emptyList(),
    @SerialName("items_count") val itemsCount: Int = 0,
    val subtotal: Double = 0.0
)
