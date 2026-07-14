package com.example.ecommerce_template.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_template.data.cart.CartItem
import com.example.ecommerce_template.data.cart.CartRepository
import com.example.ecommerce_template.core.ApiResult
import com.example.ecommerce_template.core.errorMessageOrNull
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private val _events = Channel<CartEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            cartRepository.cart.collect { cart ->
                _uiState.update {
                    it.copy(
                        items = cart?.items.orEmpty(),
                        itemsCount = cart?.itemsCount ?: 0,
                        subtotal = cart?.subtotal ?: 0.0,
                        isEmpty = cart?.isEmpty ?: true
                    )
                }
            }
        }
        viewModelScope.launch {
            cartRepository.isOnline.collect { online ->
                _uiState.update { it.copy(isOnline = online) }
            }
        }
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = cartRepository.refresh()
            _uiState.update {
                when (result) {
                    is ApiResult.Success -> it.copy(isLoading = false)
                    is ApiResult.Error -> it.copy(isLoading = false, errorMessage = result.message)
                    ApiResult.Loading -> it
                }
            }
        }
    }

    fun increase(item: CartItem) {
        viewModelScope.launch {
            val r = cartRepository.updateQuantity(item.id, item.quantity + 1)
            if (r is ApiResult.Error) _events.trySend(CartEvent.Error(r.errorMessageOrNull() ?: "Error"))
        }
    }

    fun decrease(item: CartItem) {
        viewModelScope.launch {
            if (item.quantity <= 1) {
                remove(item)
            } else {
                val r = cartRepository.updateQuantity(item.id, item.quantity - 1)
                if (r is ApiResult.Error) _events.trySend(CartEvent.Error(r.errorMessageOrNull() ?: "Error"))
            }
        }
    }

    fun remove(item: CartItem) {
        viewModelScope.launch {
            val r = cartRepository.remove(item.id)
            if (r is ApiResult.Error) _events.trySend(CartEvent.Error(r.errorMessageOrNull() ?: "Error"))
        }
    }

    fun clear() {
        viewModelScope.launch {
            val r = cartRepository.clear()
            if (r is ApiResult.Error) {
                _events.trySend(CartEvent.Error(r.errorMessageOrNull() ?: "Error"))
            } else {
                _events.trySend(CartEvent.Cleared)
            }
        }
    }

    fun consumeError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
