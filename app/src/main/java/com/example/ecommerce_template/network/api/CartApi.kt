package com.example.ecommerce_template.network.api

import com.example.ecommerce_template.network.dto.cart.CartDto
import com.example.ecommerce_template.network.dto.cart.CartItemCreateDto
import com.example.ecommerce_template.network.dto.cart.CartItemDto
import com.example.ecommerce_template.network.dto.cart.CartItemUpdateDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CartApi {
    @GET("api/v1/cart")
    suspend fun getCart(): CartDto

    @DELETE("api/v1/cart")
    suspend fun clearCart(): Response<Unit>

    @POST("api/v1/cart/items")
    suspend fun addItem(@Body body: CartItemCreateDto): CartItemDto

    @PUT("api/v1/cart/items/{itemId}")
    suspend fun updateItem(
        @Path("itemId") itemId: String,
        @Body body: CartItemUpdateDto
    ): CartItemDto

    @DELETE("api/v1/cart/items/{itemId}")
    suspend fun removeItem(@Path("itemId") itemId: String): Response<Unit>
}
