package com.example.ecommerce_template.network.api

import com.example.ecommerce_template.network.dto.user.PasswordUpdateDto
import com.example.ecommerce_template.network.dto.user.ProfileDto
import com.example.ecommerce_template.network.dto.user.ProfileUpsertDto
import com.example.ecommerce_template.network.dto.user.UserDto
import com.example.ecommerce_template.network.dto.user.UserUpdateDto
import com.example.ecommerce_template.network.dto.user.UserWithProfileDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT

interface UserApi {
    @GET("api/v1/users/me")
    suspend fun me(): UserWithProfileDto

    @PATCH("api/v1/users/me")
    suspend fun updateMe(@Body body: UserUpdateDto): UserDto

    @PUT("api/v1/users/me/password")
    suspend fun changePassword(@Body body: PasswordUpdateDto): Response<Unit>

    @PUT("api/v1/users/me/profile")
    suspend fun upsertProfile(@Body body: ProfileUpsertDto): ProfileDto
}
