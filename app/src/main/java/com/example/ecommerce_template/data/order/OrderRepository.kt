package com.example.ecommerce_template.data.order

import androidx.compose.runtime.mutableStateListOf
import com.example.ecommerce_template.data.checkOut.CheckoutSummary

object OrderRepository {
    private val _orderHistory = mutableStateListOf<CheckoutSummary>()

    val orderHistory: List<CheckoutSummary> get() = _orderHistory

    fun addOrder(summary: CheckoutSummary) {
        _orderHistory.add(summary)
    }

    fun clearHistory() {
        _orderHistory.clear()
    }
}