package com.example.ecommerce_template.data.cart

import com.example.ecommerce_template.data.product.Product

data class CartItem(
    val product: Product,
    var quantity: Int
)