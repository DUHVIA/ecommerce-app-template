package com.example.ecommerce_template.network.mapper

import com.example.ecommerce_template.data.address.Address
import com.example.ecommerce_template.network.dto.address.AddressDto
import com.example.ecommerce_template.network.dto.catalog.CategoryDto
import com.example.ecommerce_template.network.dto.catalog.ProductDto
import com.example.ecommerce_template.data.product.Product

fun AddressDto.toDomain(): Address = Address(
    id = id,
    street = street,
    city = city,
    state = state,
    zipCode = zipCode,
    isDefault = isDefault
)

fun CategoryDto.toDomain(): Category = Category(
    id = id,
    name = name,
    description = description,
    imageUrl = imageUrl
)

fun ProductDto.toDomain(): Product = Product(
    id = id,
    name = name,
    description = description.orEmpty(),
    price = price,
    categoryId = categoryId,
    categoryName = categoryName,
    imageUrl = imageUrl,
    stock = stock
)

data class Category(
    val id: String,
    val name: String,
    val description: String?,
    val imageUrl: String?
)
