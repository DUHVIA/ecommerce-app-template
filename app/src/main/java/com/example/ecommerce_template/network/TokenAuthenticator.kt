package com.example.ecommerce_template.network

import com.example.ecommerce_template.data.auth.TokenStore
import com.example.ecommerce_template.network.api.AuthApi
import com.example.ecommerce_template.network.dto.auth.RefreshRequestDto
import com.example.ecommerce_template.network.dto.auth.TokenResponseDto
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val tokenStore: TokenStore,
    private val authApiProvider: () -> AuthApi
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        val original = response.request
        if (original.header(AuthInterceptor.HEADER_SKIP_AUTH) != null) return null
        if (countPriorAttempts(response) >= 1) return null

        val newAccessToken: String? = runBlocking {
            mutex.withLock {
                val current = tokenStore.tokensNow()
                ?: return@withLock null

                val sentAuth = response.request.header("Authorization")
                if (sentAuth != null && sentAuth != "Bearer ${current.accessToken}") {
                    return@withLock current.accessToken
                }

                try {
                    val api = authApiProvider()
                    val newTokens: TokenResponseDto =
                        api.refresh(RefreshRequestDto(current.refreshToken))
                    tokenStore.save(newTokens.accessToken, newTokens.refreshToken)
                    newTokens.accessToken
                } catch (t: Throwable) {
                    tokenStore.clear()
                    null
                }
            }
        }

        if (newAccessToken == null) return null
        return original.newBuilder()
            .header("Authorization", "Bearer $newAccessToken")
            .build()
    }

    private fun countPriorAttempts(response: Response): Int {
        var count = 0
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}
