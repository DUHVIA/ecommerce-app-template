package com.example.ecommerce_template.ui.cart

import com.example.ecommerce_template.data.cart.CartItem

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val itemsCount: Int = 0,
    val subtotal: Double = 0.0,
    val isEmpty: Boolean = true,
    val isLoading: Boolean = false,
    val isOnline: Boolean = true,
    val errorMessage: String? = null
)

sealed class CartEvent {
    data class Error(val message: String) : CartEvent()
    data object Cleared : CartEvent()
}
