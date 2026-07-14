package com.example.ecommerce_template.network.dto.catalog

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: String,
    val name: String,
    val description: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("created_at") val createdAt: String
)

@Serializable
data class ProductDto(
    val id: String,
    val name: String,
    val description: String? = null,
    val price: Double,
    val stock: Int,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("category_id") val categoryId: String,
    @SerialName("category_name") val categoryName: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

@Serializable
data class PaginatedResponseDto<T>(
    val items: List<T> = emptyList(),
    val page: Int = 1,
    val size: Int = 20,
    val total: Int = 0,
    val pages: Int = 1
)
