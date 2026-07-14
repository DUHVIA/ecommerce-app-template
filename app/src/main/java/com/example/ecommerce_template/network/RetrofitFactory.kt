package com.example.ecommerce_template.network

import com.example.ecommerce_template.BuildConfig
import com.example.ecommerce_template.network.api.AddressApi
import com.example.ecommerce_template.network.api.AuthApi
import com.example.ecommerce_template.network.api.CartApi
import com.example.ecommerce_template.network.api.CatalogApi
import com.example.ecommerce_template.network.api.OrderApi
import com.example.ecommerce_template.network.api.UserApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class RetrofitFactory(
    private val authInterceptor: AuthInterceptor,
    private val tokenAuthenticator: TokenAuthenticator
) {

    private val json: Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        coerceInputValues = true
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .authenticator(tokenAuthenticator)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val authApi: AuthApi by lazy { retrofit.create(AuthApi::class.java) }
    val userApi: UserApi by lazy { retrofit.create(UserApi::class.java) }
    val catalogApi: CatalogApi by lazy { retrofit.create(CatalogApi::class.java) }
    val cartApi: CartApi by lazy { retrofit.create(CartApi::class.java) }
    val orderApi: OrderApi by lazy { retrofit.create(OrderApi::class.java) }
    val addressApi: AddressApi by lazy { retrofit.create(AddressApi::class.java) }
}
