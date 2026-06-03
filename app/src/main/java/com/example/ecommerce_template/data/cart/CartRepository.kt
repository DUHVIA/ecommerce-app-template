package com.example.ecommerce_template.data.cart

import com.example.ecommerce_template.data.product.Product

class CartRepository {

    // Caché local de productos para el carrito
    private val cartList = mutableListOf<CartItem>()

    // 1. Obtener todos los elementos del carrito
    fun getCartItems(): List<CartItem> {
        return cartList
    }

    // 2. Añadir un producto al carrito
    fun addProductToCart(product: Product) {
        // Validamos si el producto ya existe en el carrito
        val existingItem = cartList.find { it.product.id == product.id }

        if (existingItem != null) {
            // Si ya existe, solo aumentamos la cantidad
            existingItem.quantity += 1
            // No hay validación del stock
        } else {
            // Si es nuevo, lo agregamos con cantidad inicial de 1
            cartList.add(CartItem(product = product, quantity = 1))
        }
    }

    // 3. Remover o disminuir la cantidad de un producto
    fun removeProductFromCart(productId: Int) {
        val existingItem = cartList.find { it.product.id == productId }

        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                existingItem.quantity -= 1 // Si tiene varios, disminuye uno
            } else {
                cartList.remove(existingItem) // Si le quedaba uno, lo borra por completo
            }
        }
    }

    // 4. Calcular el precio total de la compra (Soles)
    fun calculateTotal(): Double {
        return cartList.sumOf { it.product.price * it.quantity }
    }


    // 5. Simular compra
    // El checkout ahora es directo y limpia el carrito simulando el éxito
    fun performCheckout(): Boolean {
        if (cartList.isEmpty()) return false

        // Simula que se procesó el pago del usuario "usr_001"
        cartList.clear()
        return true
    }
}