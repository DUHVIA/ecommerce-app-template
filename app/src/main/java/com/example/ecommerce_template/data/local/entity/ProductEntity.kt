package com.example.ecommerce_template.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa un Producto en la base de datos local (Room).
 * Se mapea directamente con el modelo de dominio [Product].
 */
@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageRes: Int,
    val imageUrl: String? = null,
    val stock: Int
)
