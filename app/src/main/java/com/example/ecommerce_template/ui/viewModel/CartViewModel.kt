package com.example.ecommerce_template.ui.viewModel

import com.example.ecommerce_template.data.cart.CartItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_template.data.cart.CartRepository
import com.example.ecommerce_template.data.product.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val taxes: Double = 0.0,
    val total: Double = 0.0,
    val isEmpty: Boolean = true
)

class CartViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        observeCart()
    }

    private fun observeCart() {
        viewModelScope.launch {

            CartRepository.cartItems.collect { items ->

                val subtotal = items.sumOf {
                    it.product.price * it.quantity
                }

                val taxes = subtotal * 0.18

                _uiState.value = CartUiState(
                    items = items,
                    subtotal = subtotal,
                    taxes = taxes,
                    total = subtotal + taxes,
                    isEmpty = items.isEmpty()
                )
            }
        }
    }

    fun addProduct(product: Product) {
        CartRepository.addProductToCart(product)
    }

    fun removeProduct(productId: Int) {
        CartRepository.removeProductFromCart(productId)
    }

    fun clearCart() {
        CartRepository.clearCart()
    }
}