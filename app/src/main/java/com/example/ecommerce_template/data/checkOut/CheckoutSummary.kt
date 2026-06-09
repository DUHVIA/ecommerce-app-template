package com.example.ecommerce_template.data.checkOut

data class CheckoutSummary(
    val items: List<String>,
    val subtotal: Double,
    val taxes: Double,
    val shipping: Double,
    val total: Double,
    val timestamp: Long = System.currentTimeMillis()
)
