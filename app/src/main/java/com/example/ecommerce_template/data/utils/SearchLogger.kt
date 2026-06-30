package com.example.ecommerce_template.data.utils

import android.util.Log

class SearchLogger(
    private val sessionManager: SessionManager
) {
    fun saveSearch(text: String) {
        val user = sessionManager.getCurrentUser()
        // Registramos en el Logcat de Android Studio
        Log.d("IronCoreLogger", "El usuario [$user] ha buscado: $text")
    }
}