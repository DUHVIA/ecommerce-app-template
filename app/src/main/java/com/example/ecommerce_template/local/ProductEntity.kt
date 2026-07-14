package com.example.ecommerce_template.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val categoryId: String,
    val categoryName: String,
    val imageUrl: String?,
    val stock: Int,
    val cachedAt: Long
)
