package com.example.ecommerce_template.data.cart

import com.example.ecommerce_template.data.product.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object CartRepository {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addProductToCart(product: Product) {

        val currentItems = _cartItems.value

        val existingItem =
            currentItems.find { it.product.id == product.id }

        _cartItems.value =
            if (existingItem != null) {

                currentItems.map {
                    if (it.product.id == product.id) {
                        it.copy(quantity = it.quantity + 1)
                    } else {
                        it
                    }
                }

            } else {

                currentItems + CartItem(
                    product = product,
                    quantity = 1
                )
            }
    }

    fun removeProductFromCart(productId: Int) {

        val currentItems = _cartItems.value

        val existingItem =
            currentItems.find { it.product.id == productId }

        if (existingItem == null) return

        _cartItems.value =
            if (existingItem.quantity > 1) {

                currentItems.map {
                    if (it.product.id == productId) {
                        it.copy(quantity = it.quantity - 1)
                    } else {
                        it
                    }
                }

            } else {

                currentItems.filter {
                    it.product.id != productId
                }
            }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun calculateTotal(): Double {
        return _cartItems.value.sumOf {
            it.product.price * it.quantity
        }
    }

    fun getOrderSummary(): OrderSummary {

        val subtotal = calculateTotal()
        val taxes = subtotal * 0.18

        return OrderSummary(
            subtotal = subtotal,
            taxes = taxes,
            total = subtotal + taxes
        )
    }
}