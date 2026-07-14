package com.example.ecommerce_template.data.cart

import com.example.ecommerce_template.core.ApiResult
import com.example.ecommerce_template.core.safeCall
import com.example.ecommerce_template.network.NetworkMonitor
import com.example.ecommerce_template.network.api.CartApi
import com.example.ecommerce_template.network.dto.cart.CartItemCreateDto
import com.example.ecommerce_template.network.dto.cart.CartItemUpdateDto
import com.example.ecommerce_template.network.mapper.toDomain
import com.example.ecommerce_template.core.SearchLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartRepository(
    private val cartApi: CartApi,
    private val networkMonitor: NetworkMonitor,
    private val logger: SearchLogger
) {
    private val _cart = MutableStateFlow<Cart?>(null)
    val cart: StateFlow<Cart?> = _cart.asStateFlow()

    val isOnline: StateFlow<Boolean> get() = networkMonitor.isOnline

    private fun offlineError(): ApiResult.Error =
        ApiResult.Error(message = "Sin conexión — vuelve cuando tengas red")

    private inline fun <T> requireOnline(orElse: () -> ApiResult<T>): ApiResult<T>? =
        if (networkMonitor.isOnline.value) null else orElse()

    suspend fun refresh(): ApiResult<Cart> {
        requireOnline<Cart> { offlineError() }?.let { return it }
        val result = safeCall { cartApi.getCart() }
        if (result is ApiResult.Success) {
            _cart.value = result.data.toDomain()
        }
        return result.map { it.toDomain() }
    }

    suspend fun add(productId: String, quantity: Int = 1): ApiResult<CartItem> {
        requireOnline<CartItem> { offlineError() }?.let { return it }
        logger.saveSearch("Agregó el producto al carrito (ID: $productId, qty: $quantity)")
        val result = safeCall { cartApi.addItem(CartItemCreateDto(productId, quantity)) }
        if (result is ApiResult.Success) refresh()
        return result.map { it.toDomain() }
    }

    suspend fun updateQuantity(itemId: String, quantity: Int): ApiResult<CartItem> {
        requireOnline<CartItem> { offlineError() }?.let { return it }
        val result = safeCall { cartApi.updateItem(itemId, CartItemUpdateDto(quantity)) }
        if (result is ApiResult.Success) refresh()
        return result.map { it.toDomain() }
    }

    suspend fun remove(itemId: String): ApiResult<Unit> {
        requireOnline<Unit> { offlineError() }?.let { return it }
        logger.saveSearch("Eliminó el item del carrito (item ID: $itemId)")
        val result = safeCall {
            cartApi.removeItem(itemId)
            Unit
        }
        if (result is ApiResult.Success) refresh()
        return result
    }

    suspend fun clear(): ApiResult<Unit> {
        requireOnline<Unit> { offlineError() }?.let { return it }
        logger.saveSearch("Vació por completo el carrito de compras")
        val result = safeCall {
            cartApi.clearCart()
            Unit
        }
        if (result is ApiResult.Success) {
            _cart.value = Cart(items = emptyList(), itemsCount = 0, subtotal = 0.0)
        }
        return result
    }
}
