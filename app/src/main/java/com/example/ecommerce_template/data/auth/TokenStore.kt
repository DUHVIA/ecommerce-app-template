package com.example.ecommerce_template.data.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

data class TokenPair(
    val accessToken: String,
    val refreshToken: String
)

private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_tokens")

class TokenStore(private val context: Context) {

    private val accessKey = stringPreferencesKey("access_token")
    private val refreshKey = stringPreferencesKey("refresh_token")

    val tokens: Flow<TokenPair?> = context.tokenDataStore.data.map { prefs ->
        val access = prefs[accessKey]
        val refresh = prefs[refreshKey]
        if (access != null && refresh != null) TokenPair(access, refresh) else null
    }

    suspend fun tokensNow(): TokenPair? = tokens.firstOrNull()

    suspend fun save(accessToken: String, refreshToken: String) {
        context.tokenDataStore.edit { prefs ->
            prefs[accessKey] = accessToken
            prefs[refreshKey] = refreshToken
        }
    }

    suspend fun clear() {
        context.tokenDataStore.edit { prefs ->
            prefs.remove(accessKey)
            prefs.remove(refreshKey)
        }
    }
}
