package com.example.ecommerce_template.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_template.data.address.Address
import com.example.ecommerce_template.data.address.AddressRepository
import com.example.ecommerce_template.data.cart.Cart
import com.example.ecommerce_template.data.cart.CartRepository
import com.example.ecommerce_template.core.ApiResult
import com.example.ecommerce_template.core.errorMessageOrNull
import com.example.ecommerce_template.network.NetworkMonitor
import com.example.ecommerce_template.data.order.Order
import com.example.ecommerce_template.data.order.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val addressRepository: AddressRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            cartRepository.cart.collect { cart ->
                val items = cart?.items.orEmpty().map { item ->
                    "(x${item.quantity}) ${item.productName}" to "S/ %.2f".format(item.subtotal)
                }
                _uiState.update {
                    it.copy(
                        cart = cart,
                        items = items,
                        subtotal = cart?.subtotal ?: 0.0,
                        taxes = (cart?.subtotal ?: 0.0) * 0.0,
                        shipping = 0.0,
                        total = cart?.subtotal ?: 0.0
                    )
                }
            }
        }
        viewModelScope.launch {
            networkMonitor.isOnline.collect { online ->
                _uiState.update { it.copy(isOnline = online) }
            }
        }
        refresh()
    }

    fun refresh() {
        if (!networkMonitor.isOnline.value) {
            _uiState.update { it.copy(isOnline = false) }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            cartRepository.refresh()
            val addressesResult = addressRepository.list()
            val addresses = if (addressesResult is ApiResult.Success) addressesResult.data else emptyList()
            val selected = addresses.firstOrNull { it.isDefault }?.id
                ?: addresses.firstOrNull()?.id
            _uiState.update {
                it.copy(
                    addresses = addresses,
                    selectedAddressId = selected,
                    isLoading = false
                )
            }
            if (addressesResult is ApiResult.Error) {
                _uiState.update { it.copy(errorMessage = addressesResult.message) }
            }
        }
    }

    fun selectAddress(id: String) {
        _uiState.update { it.copy(selectedAddressId = id) }
    }

    fun openAddressSheet() {
        if (!networkMonitor.isOnline.value) {
            _uiState.update { it.copy(errorMessage = "Sin conexión — vuelve cuando tengas red") }
            return
        }
        _uiState.update { it.copy(isAddressSheetOpen = true) }
    }

    fun closeAddressSheet() {
        _uiState.update { it.copy(isAddressSheetOpen = false, isAddressFormOpen = false) }
    }

    fun openAddressForm() {
        if (!networkMonitor.isOnline.value) {
            _uiState.update { it.copy(errorMessage = "Sin conexión — vuelve cuando tengas red") }
            return
        }
        _uiState.update { it.copy(isAddressFormOpen = true) }
    }

    fun closeAddressForm() {
        _uiState.update { it.copy(isAddressFormOpen = false) }
    }

    fun updateAddressForm(form: AddressFormState) {
        _uiState.update { it.copy(addressForm = form) }
    }

    fun createAddress() {
        if (!networkMonitor.isOnline.value) {
            _uiState.update { it.copy(errorMessage = "Sin conexión — vuelve cuando tengas red") }
            return
        }
        val form = _uiState.value.addressForm
        if (form.street.isBlank() || form.city.isBlank() || form.state.isBlank() || form.zipCode.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Completa todos los campos de la dirección") }
            return
        }
        viewModelScope.launch {
            val r = addressRepository.create(
                street = form.street,
                city = form.city,
                state = form.state,
                zipCode = form.zipCode,
                isDefault = form.isDefault
            )
            when (r) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(
                            addresses = it.addresses + r.data,
                            selectedAddressId = r.data.id,
                            isAddressFormOpen = false,
                            isAddressSheetOpen = false,
                            addressForm = AddressFormState()
                        )
                    }
                }
                is ApiResult.Error -> _uiState.update { it.copy(errorMessage = r.message) }
                ApiResult.Loading -> Unit
            }
        }
    }

    fun placeOrder(onPlaced: () -> Unit) {
        if (!networkMonitor.isOnline.value) {
            _uiState.update { it.copy(errorMessage = "Sin conexión — vuelve cuando tengas red") }
            return
        }
        val addressId = _uiState.value.selectedAddressId
        if (addressId == null) {
            _uiState.update { it.copy(errorMessage = "Selecciona una dirección de envío") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isPlacingOrder = true) }
            val result = orderRepository.checkout(addressId)
            _uiState.update { it.copy(isPlacingOrder = false) }
            when (result) {
                is ApiResult.Success -> {
                    cartRepository.clear()
                    onPlaced()
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(errorMessage = result.errorMessageOrNull()) }
                }
                ApiResult.Loading -> Unit
            }
        }
    }

    fun consumeError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
