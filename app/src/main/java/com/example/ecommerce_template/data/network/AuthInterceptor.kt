package com.example.ecommerce_template.data.network

import com.example.ecommerce_template.data.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Obtenemos el token de manera síncrona para inyectarlo en la petición actual
        val token = tokenManager.getTokenSync()

        // Si no hay token, continuamos con la petición original
        if (token.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        // Si hay token, lo añadimos al header Authorization
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newRequest)
    }
}
