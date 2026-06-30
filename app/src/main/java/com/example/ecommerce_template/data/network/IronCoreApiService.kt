package com.example.ecommerce_template.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IronCoreApiService {

    @POST("api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<TokenResponse>

    @GET("api/v1/products")
    suspend fun getProducts(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): Response<PaginatedProductResponse>

}
