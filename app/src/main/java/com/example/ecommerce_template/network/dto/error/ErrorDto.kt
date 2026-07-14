package com.example.ecommerce_template.network.dto.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    @SerialName("detail") val detail: String? = null,
    @SerialName("error_code") val errorCode: String? = null
)

@Serializable
data class ValidationErrorDto(
    @SerialName("loc") val loc: List<kotlinx.serialization.json.JsonElement> = emptyList(),
    @SerialName("msg") val msg: String = "",
    @SerialName("type") val type: String = ""
)

@Serializable
data class HttpValidationErrorDto(
    @SerialName("detail") val detail: List<ValidationErrorDto> = emptyList()
)
