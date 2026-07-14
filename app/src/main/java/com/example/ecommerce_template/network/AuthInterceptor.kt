package com.example.ecommerce_template.network

import com.example.ecommerce_template.data.auth.TokenStore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenStore: TokenStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val skipAuth = request.header(HEADER_SKIP_AUTH) != null
        val builder = request.newBuilder().removeHeader(HEADER_SKIP_AUTH)

        if (!skipAuth) {
            val tokenPair = runBlocking { tokenStore.tokens.first() }
            if (tokenPair != null) {
                builder.addHeader("Authorization", "Bearer ${tokenPair.accessToken}")
            }
        }

        return chain.proceed(builder.build())
    }

    companion object {
        const val HEADER_SKIP_AUTH = "X-Skip-Auth"
    }
}
