package com.example.ecommerce_template.local

import com.example.ecommerce_template.data.product.Product

fun ProductEntity.toDomain(): Product = Product(
    id = id,
    name = name,
    description = description,
    price = price,
    categoryId = categoryId,
    categoryName = categoryName,
    imageUrl = imageUrl,
    stock = stock
)

fun Product.toEntity(cachedAt: Long): ProductEntity = ProductEntity(
    id = id,
    name = name,
    description = description,
    price = price,
    categoryId = categoryId,
    categoryName = categoryName,
    imageUrl = imageUrl,
    stock = stock,
    cachedAt = cachedAt
)
