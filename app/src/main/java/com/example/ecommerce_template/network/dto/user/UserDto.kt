package com.example.ecommerce_template.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    @SerialName("created_at") val createdAt: String
)

@Serializable
data class UserWithProfileDto(
    val id: String,
    val name: String,
    val email: String,
    @SerialName("created_at") val createdAt: String,
    val profile: ProfileDto? = null
)

@Serializable
data class UserUpdateDto(
    val name: String? = null,
    val email: String? = null
)

@Serializable
data class PasswordUpdateDto(
    @SerialName("current_password") val currentPassword: String,
    @SerialName("new_password") val newPassword: String
)

@Serializable
data class ProfileDto(
    val id: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("phone_number") val phoneNumber: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

@Serializable
data class ProfileUpsertDto(
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("phone_number") val phoneNumber: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null
)
