package com.example.ecommerce_template.network.api

import com.example.ecommerce_template.network.dto.address.AddressCreateDto
import com.example.ecommerce_template.network.dto.address.AddressDto
import com.example.ecommerce_template.network.dto.address.AddressUpdateDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddressApi {
    @GET("api/v1/addresses")
    suspend fun list(): List<AddressDto>

    @POST("api/v1/addresses")
    suspend fun create(@Body body: AddressCreateDto): AddressDto

    @GET("api/v1/addresses/{addressId}")
    suspend fun get(@Path("addressId") addressId: String): AddressDto

    @PUT("api/v1/addresses/{addressId}")
    suspend fun update(
        @Path("addressId") addressId: String,
        @Body body: AddressUpdateDto
    ): AddressDto

    @DELETE("api/v1/addresses/{addressId}")
    suspend fun delete(@Path("addressId") addressId: String): Response<Unit>

    @PATCH("api/v1/addresses/{addressId}/default")
    suspend fun setDefault(@Path("addressId") addressId: String): AddressDto
}
