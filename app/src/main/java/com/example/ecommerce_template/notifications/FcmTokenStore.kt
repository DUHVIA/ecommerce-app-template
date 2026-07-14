package com.example.ecommerce_template.notifications

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private val Context.fcmDataStore: DataStore<Preferences> by preferencesDataStore(name = "fcm_tokens")

class FcmTokenStore(private val context: Context) {

    private val fcmTokenKey = stringPreferencesKey("fcm_token")

    val token: Flow<String?> = context.fcmDataStore.data.map { prefs ->
        prefs[fcmTokenKey]
    }

    suspend fun tokenNow(): String? = token.firstOrNull()

    suspend fun saveToken(token: String) {
        context.fcmDataStore.edit { prefs ->
            prefs[fcmTokenKey] = token
        }
    }

    suspend fun clearToken() {
        context.fcmDataStore.edit { prefs ->
            prefs.remove(fcmTokenKey)
        }
    }
}
