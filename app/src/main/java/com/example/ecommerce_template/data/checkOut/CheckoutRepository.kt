package com.example.ecommerce_template.data.checkOut

import com.example.ecommerce_template.data.cart.CartRepository

object CheckoutRepository {

    fun prepareSummary(): CheckoutSummary {

        val items = CartRepository.cartItems.value

        val cartItems = items.map { item ->
            "(x${item.quantity}) ${item.product.name}"
        }

        val subtotal = CartRepository.calculateTotal()
        val taxes = subtotal * 0.18
        val shipping = 10.0

        return CheckoutSummary(
            items = cartItems,
            subtotal = subtotal,
            taxes = taxes,
            shipping = shipping,
            total = subtotal + taxes + shipping
        )
    }
}