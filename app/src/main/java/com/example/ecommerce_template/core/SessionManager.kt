package com.example.ecommerce_template.core

import com.example.ecommerce_template.data.auth.TokenStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionManager(
    private val tokenStore: TokenStore
) {

    val hasSession: Flow<Boolean> = tokenStore.tokens.map { it != null }

    suspend fun isLoggedIn(): Boolean = tokenStore.tokensNow() != null
}
