package com.example.ecommerce_template.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_template.data.checkOut.CheckoutSummary
import com.example.ecommerce_template.data.order.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class OrderHistoryUiState(
    val orders: List<CheckoutSummary> = emptyList(),
    val isEmpty: Boolean = true
)

class OrderHistoryViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(OrderHistoryUiState())

    val uiState =
        _uiState.asStateFlow()

    init {
        observeOrders()
    }

    private fun observeOrders() {

        viewModelScope.launch {

            OrderRepository.orderHistory.collect { orders ->

                _uiState.value =
                    OrderHistoryUiState(
                        orders = orders,
                        isEmpty = orders.isEmpty()
                    )
            }
        }
    }
}