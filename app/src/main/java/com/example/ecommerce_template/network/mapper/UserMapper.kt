package com.example.ecommerce_template.network.mapper

import com.example.ecommerce_template.data.auth.User
import com.example.ecommerce_template.network.dto.user.ProfileDto
import com.example.ecommerce_template.network.dto.user.UserDto
import com.example.ecommerce_template.network.dto.user.UserWithProfileDto
import com.example.ecommerce_template.data.profile.Profile

fun UserDto.toDomain(): User = User(
    id = id,
    name = name,
    email = email
)

fun UserWithProfileDto.toDomain(): User = User(
    id = id,
    name = name,
    email = email
)

fun ProfileDto.toDomain(): Profile = Profile(
    id = id,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    avatarUrl = avatarUrl
)
