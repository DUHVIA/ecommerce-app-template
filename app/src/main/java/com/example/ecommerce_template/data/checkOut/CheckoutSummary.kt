package com.example.ecommerce_template.data.checkOut

import com.example.ecommerce_template.data.cart.CartItem

data class CheckoutSummary(
    val items: List<String>,
    val subtotal: Double,
    val taxes: Double,
    val shipping: Double,
    val total: Double,
    val timestamp: Long = System.currentTimeMillis()
)
