package com.example.ecommerce_template.data.product

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageRes: Int,
    val imageUrl: String? = null,
    val stock: Int
)
