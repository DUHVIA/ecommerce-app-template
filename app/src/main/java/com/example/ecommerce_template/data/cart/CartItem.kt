package com.example.ecommerce_template.data.cart

data class CartItem(
    val id: String,
    val productId: String,
    val productName: String,
    val productImageUrl: String?,
    val unitPrice: Double,
    val subtotal: Double,
    val quantity: Int
)

data class Cart(
    val items: List<CartItem>,
    val itemsCount: Int,
    val subtotal: Double
) {
    val isEmpty: Boolean get() = items.isEmpty()
}
