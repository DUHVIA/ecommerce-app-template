package com.example.ecommerce_template.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_template.core.ApiResult
import com.example.ecommerce_template.data.order.Order
import com.example.ecommerce_template.data.order.OrderRepository
import com.example.ecommerce_template.data.order.OrderStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderHistoryViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderHistoryUiState())
    val uiState: StateFlow<OrderHistoryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            orderRepository.orders.collect { orders ->
                _uiState.update {
                    it.copy(orders = orders, isEmpty = orders.isEmpty())
                }
            }
        }
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val r = orderRepository.refresh()
            _uiState.update {
                when (r) {
                    is ApiResult.Success -> it.copy(isLoading = false)
                    is ApiResult.Error -> it.copy(isLoading = false, errorMessage = r.message)
                    ApiResult.Loading -> it
                }
            }
        }
    }

    fun cancel(order: Order) {
        if (order.status !in setOf(OrderStatus.PENDING, OrderStatus.PAID)) return
        viewModelScope.launch {
            val r = orderRepository.cancel(order.id)
            if (r is ApiResult.Error) {
                _uiState.update { it.copy(errorMessage = r.message) }
            }
        }
    }
}
