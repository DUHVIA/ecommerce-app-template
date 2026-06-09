package com.example.ecommerce_template.data.order

import com.example.ecommerce_template.data.checkOut.CheckoutSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object OrderRepository {

    private val _orderHistory =
        MutableStateFlow<List<CheckoutSummary>>(emptyList())

    val orderHistory: StateFlow<List<CheckoutSummary>> =
        _orderHistory.asStateFlow()

    fun addOrder(summary: CheckoutSummary) {

        _orderHistory.value =
            _orderHistory.value + summary
    }

    fun clearHistory() {

        _orderHistory.value =
            emptyList()
    }
}