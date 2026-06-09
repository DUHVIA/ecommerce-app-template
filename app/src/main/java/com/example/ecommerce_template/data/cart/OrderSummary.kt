package com.example.ecommerce_template.data.cart

data class OrderSummary(
    val subtotal: Double,
    val taxes: Double,
    val total: Double
)