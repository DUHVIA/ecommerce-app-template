package com.example.ecommerce_template.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_template.data.cart.CartRepository
import com.example.ecommerce_template.data.checkOut.CheckoutRepository
import com.example.ecommerce_template.data.order.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CheckoutUiState(
    val items: List<String> = emptyList(),
    val subtotal: Double = 0.0,
    val taxes: Double = 0.0,
    val shipping: Double = 0.0,
    val total: Double = 0.0,
    val isEmpty: Boolean = true
)

class CheckoutViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(CheckoutUiState())

    val uiState: StateFlow<CheckoutUiState> =
        _uiState.asStateFlow()

    init {
        observeCart()
    }

    private fun observeCart() {

        viewModelScope.launch {

            CartRepository.cartItems.collect { items ->

                val summary =
                    CheckoutRepository.prepareSummary(items)

                _uiState.value =
                    CheckoutUiState(
                        items = summary.items,
                        subtotal = summary.subtotal,
                        taxes = summary.taxes,
                        shipping = summary.shipping,
                        total = summary.total,
                        isEmpty = items.isEmpty()
                    )
            }
        }
    }

    fun confirmPurchase(onOrderPlaced: () -> Unit) {

        val summary = CheckoutRepository.prepareSummary(
            CartRepository.cartItems.value
        )

        OrderRepository.addOrder(summary)

        CartRepository.clearCart()

        onOrderPlaced()
    }
}