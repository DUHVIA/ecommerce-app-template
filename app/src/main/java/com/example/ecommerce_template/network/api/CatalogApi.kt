package com.example.ecommerce_template.network.api

import com.example.ecommerce_template.network.dto.catalog.CategoryDto
import com.example.ecommerce_template.network.dto.catalog.PaginatedResponseDto
import com.example.ecommerce_template.network.dto.catalog.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatalogApi {
    @GET("api/v1/categories")
    suspend fun categories(): List<CategoryDto>

    @GET("api/v1/categories/{categoryId}")
    suspend fun categoryById(@Path("categoryId") categoryId: String): CategoryDto

    @GET("api/v1/products")
    suspend fun products(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 100,
        @Query("category_id") categoryId: String? = null,
        @Query("q") q: String? = null,
        @Query("min_price") minPrice: Double? = null,
        @Query("max_price") maxPrice: Double? = null,
        @Query("in_stock") inStock: Boolean? = null
    ): PaginatedResponseDto<ProductDto>

    @GET("api/v1/products/{productId}")
    suspend fun productById(@Path("productId") productId: String): ProductDto
}
