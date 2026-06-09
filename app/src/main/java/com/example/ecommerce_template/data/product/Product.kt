package com.example.ecommerce_template.data.product

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String?,
    val stock: Int
)
