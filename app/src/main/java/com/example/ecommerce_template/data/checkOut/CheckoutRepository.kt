package com.example.ecommerce_template.data.checkOut

import com.example.ecommerce_template.data.cart.CartItem
import com.example.ecommerce_template.data.cart.CartRepository

object CheckoutRepository {

    fun prepareSummary(
        items: List<CartItem>
    ): CheckoutSummary {

        val cartItems = items.map {
            "(x${it.quantity}) ${it.product.name}"
        }

        val subtotal = items.sumOf {
            it.product.price * it.quantity
        }

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