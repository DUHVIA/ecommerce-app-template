package com.example.ecommerce_template.data.order

import com.example.ecommerce_template.core.ApiResult
import com.example.ecommerce_template.core.safeCall
import com.example.ecommerce_template.network.api.OrderApi
import com.example.ecommerce_template.network.dto.order.OrderCreateDto
import com.example.ecommerce_template.network.mapper.toDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderRepository(
    private val orderApi: OrderApi
) {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    suspend fun refresh(status: String? = null): ApiResult<List<Order>> {
        val result = safeCall { orderApi.list(size = 100, status = status) }
        if (result is ApiResult.Success) {
            _orders.value = result.data.items.map { it.toDomain() }
        }
        return result.map { it.items.map { dto -> dto.toDomain() } }
    }

    suspend fun checkout(shippingAddressId: String): ApiResult<Order> {
        val result = safeCall { orderApi.create(OrderCreateDto(shippingAddressId = shippingAddressId)) }
        if (result is ApiResult.Success) {
            _orders.value = listOf(result.data.toDomain()) + _orders.value
        }
        return result.map { it.toDomain() }
    }

    suspend fun getById(orderId: String): ApiResult<Order> =
        safeCall { orderApi.getById(orderId) }.map { it.toDomain() }

    suspend fun cancel(orderId: String): ApiResult<Order> {
        val result = safeCall { orderApi.cancel(orderId) }
        if (result is ApiResult.Success) {
            val updated = result.data.toDomain()
            _orders.value = _orders.value.map { if (it.id == updated.id) updated else it }
        }
        return result.map { it.toDomain() }
    }
}
