package com.example.ecommerce_template.network.dto.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderCreateDto(
    @SerialName("shipping_address_id") val shippingAddressId: String
)

@Serializable
data class OrderItemDto(
    val id: String,
    @SerialName("product_id") val productId: String,
    @SerialName("product_name") val productName: String,
    val quantity: Int,
    @SerialName("unit_price") val unitPrice: Double,
    val subtotal: Double
)

@Serializable
data class OrderDto(
    val id: String,
    val status: String,
    @SerialName("shipping_address") val shippingAddress: com.example.ecommerce_template.network.dto.address.AddressDto,
    val subtotal: Double,
    val taxes: Double,
    @SerialName("shipping_cost") val shippingCost: Double,
    val total: Double,
    val items: List<OrderItemDto> = emptyList(),
    @SerialName("created_at") val createdAt: String
)
