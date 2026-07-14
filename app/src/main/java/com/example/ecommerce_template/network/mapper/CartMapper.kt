package com.example.ecommerce_template.network.mapper

import com.example.ecommerce_template.data.cart.Cart
import com.example.ecommerce_template.data.cart.CartItem
import com.example.ecommerce_template.network.dto.cart.CartDto
import com.example.ecommerce_template.network.dto.cart.CartItemDto

fun CartItemDto.toDomain(): CartItem = CartItem(
    id = id,
    productId = productId,
    productName = productName,
    productImageUrl = productImageUrl,
    unitPrice = unitPrice,
    subtotal = subtotal,
    quantity = quantity
)

fun CartDto.toDomain(): Cart = Cart(
    items = items.map { it.toDomain() },
    itemsCount = itemsCount,
    subtotal = subtotal
)
