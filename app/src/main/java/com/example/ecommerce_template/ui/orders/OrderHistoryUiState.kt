package com.example.ecommerce_template.ui.orders

import com.example.ecommerce_template.data.order.Order

data class OrderHistoryUiState(
    val orders: List<Order> = emptyList(),
    val isEmpty: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
