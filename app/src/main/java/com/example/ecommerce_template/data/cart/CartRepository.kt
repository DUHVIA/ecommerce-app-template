package com.example.ecommerce_template.data.cart

import androidx.compose.runtime.mutableStateListOf
import com.example.ecommerce_template.data.product.Product

object CartRepository {

    // Usamos mutableStateListOf para que Compose detecte cambios automáticamente
    private val _cartList = mutableStateListOf<CartItem>()

    // Exponemos una lista de solo lectura hacia afuera
    val cartItems: List<CartItem> get() = _cartList

    // 2. Añadir un producto al carrito
    fun addProductToCart(product: Product) {
        val existingItem = _cartList.find { it.product.id == product.id }

        if (existingItem != null) {
            val index = _cartList.indexOf(existingItem)
            _cartList[index] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            _cartList.add(CartItem(product = product, quantity = 1))
        }
    }

    // 3. Remover o disminuir la cantidad de un producto
    fun removeProductFromCart(productId: Int) {
        val existingItem = _cartList.find { it.product.id == productId }

        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                val index = _cartList.indexOf(existingItem)
                _cartList[index] = existingItem.copy(quantity = existingItem.quantity - 1)
            } else {
                _cartList.remove(existingItem)
            }
        }
    }

    // 4. Calcular el precio total
    fun calculateTotal(): Double {
        return _cartList.sumOf { it.product.price * it.quantity }
    }

    // 5. Resumen del checkout del carrito
    fun getOrderSummary(): OrderSummary {
        val subtotal = calculateTotal()
        val taxes = subtotal * 0.18

        return OrderSummary(
            subtotal = subtotal,
            taxes = taxes,
            total = subtotal + taxes
        )
    }

    // 6. Nueva función para limpiar el carrito
    fun clearCart() {
        _cartList.clear()
    }
}