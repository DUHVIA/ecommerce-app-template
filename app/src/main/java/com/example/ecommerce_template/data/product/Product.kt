package com.example.ecommerce_template.data.product

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val categoryId: String,
    val categoryName: String,
    val imageUrl: String?,
    val stock: Int
)
