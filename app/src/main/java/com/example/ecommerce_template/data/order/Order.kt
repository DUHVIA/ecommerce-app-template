package com.example.ecommerce_template.data.order

import com.example.ecommerce_template.data.address.Address

enum class OrderStatus {
    PENDING, PAID, PROCESSING, SHIPPED, DELIVERED, CANCELLED, UNKNOWN;

    companion object {
        fun fromApi(raw: String?): OrderStatus = when (raw?.lowercase()) {
            "pending" -> PENDING
            "paid" -> PAID
            "processing" -> PROCESSING
            "shipped" -> SHIPPED
            "delivered" -> DELIVERED
            "cancelled", "canceled" -> CANCELLED
            else -> UNKNOWN
        }
    }
}

data class OrderItem(
    val id: String,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val unitPrice: Double,
    val subtotal: Double
)

data class Order(
    val id: String,
    val status: OrderStatus,
    val rawStatus: String,
    val shippingAddress: Address,
    val subtotal: Double,
    val taxes: Double,
    val shippingCost: Double,
    val total: Double,
    val items: List<OrderItem>,
    val createdAt: String
)
