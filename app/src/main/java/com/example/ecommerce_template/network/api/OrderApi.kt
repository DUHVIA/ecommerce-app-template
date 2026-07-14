package com.example.ecommerce_template.network.api

import com.example.ecommerce_template.network.dto.order.OrderCreateDto
import com.example.ecommerce_template.network.dto.order.OrderDto
import com.example.ecommerce_template.network.dto.catalog.PaginatedResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderApi {
    @POST("api/v1/orders")
    suspend fun create(@Body body: OrderCreateDto): OrderDto

    @GET("api/v1/orders")
    suspend fun list(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 100,
        @Query("status") status: String? = null
    ): PaginatedResponseDto<OrderDto>

    @GET("api/v1/orders/{orderId}")
    suspend fun getById(@Path("orderId") orderId: String): OrderDto

    @PATCH("api/v1/orders/{orderId}/cancel")
    suspend fun cancel(@Path("orderId") orderId: String): OrderDto
}
