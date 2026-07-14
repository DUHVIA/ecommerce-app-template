package com.example.ecommerce_template.ui.cart

import com.example.ecommerce_template.data.address.Address
import com.example.ecommerce_template.data.cart.Cart
import com.example.ecommerce_template.data.order.Order

data class AddressFormState(
    val street: String = "",
    val city: String = "",
    val state: String = "",
    val zipCode: String = "",
    val isDefault: Boolean = true
)

data class CheckoutUiState(
    val cart: Cart? = null,
    val items: List<Pair<String, String>> = emptyList(),
    val subtotal: Double = 0.0,
    val taxes: Double = 0.0,
    val shipping: Double = 0.0,
    val total: Double = 0.0,
    val addresses: List<Address> = emptyList(),
    val selectedAddressId: String? = null,
    val addressForm: AddressFormState = AddressFormState(),
    val isAddressSheetOpen: Boolean = false,
    val isAddressFormOpen: Boolean = false,
    val isLoading: Boolean = false,
    val isPlacingOrder: Boolean = false,
    val isOnline: Boolean = true,
    val errorMessage: String? = null
) {
    val isEmpty: Boolean get() = cart?.isEmpty ?: true
}

sealed class CheckoutEvent {
    data class Error(val message: String) : CheckoutEvent()
    data class OrderPlaced(val order: Order) : CheckoutEvent()
}
