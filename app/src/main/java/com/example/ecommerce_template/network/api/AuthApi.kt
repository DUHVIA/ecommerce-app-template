package com.example.ecommerce_template.network.api

import com.example.ecommerce_template.network.dto.auth.LoginRequestDto
import com.example.ecommerce_template.network.dto.auth.RefreshRequestDto
import com.example.ecommerce_template.network.dto.auth.RegisterRequestDto
import com.example.ecommerce_template.network.dto.auth.TokenResponseDto
import com.example.ecommerce_template.network.dto.user.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/v1/auth/register")
    suspend fun register(@Body body: RegisterRequestDto): UserDto

    @POST("api/v1/auth/login")
    suspend fun login(@Body body: LoginRequestDto): TokenResponseDto

    @POST("api/v1/auth/refresh")
    suspend fun refresh(@Body body: RefreshRequestDto): TokenResponseDto
}
