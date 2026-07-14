package com.example.ecommerce_template.network.dto.address

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    val id: String,
    val street: String,
    val city: String,
    val state: String,
    @SerialName("zip_code") val zipCode: String,
    @SerialName("is_default") val isDefault: Boolean = false,
    @SerialName("created_at") val createdAt: String
)

@Serializable
data class AddressCreateDto(
    val street: String,
    val city: String,
    val state: String,
    @SerialName("zip_code") val zipCode: String,
    @SerialName("is_default") val isDefault: Boolean = false
)

@Serializable
data class AddressUpdateDto(
    val street: String? = null,
    val city: String? = null,
    val state: String? = null,
    @SerialName("zip_code") val zipCode: String? = null
)
