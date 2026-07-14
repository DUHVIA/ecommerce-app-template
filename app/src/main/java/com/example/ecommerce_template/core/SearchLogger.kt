package com.example.ecommerce_template.core

import android.util.Log

class SearchLogger {

    @Volatile
    private var cachedUser: String = "Invitado"

    fun setCurrentUser(name: String) {
        cachedUser = name
    }

    fun saveSearch(text: String) {
        Log.d("SearchLogger", "Usuario: '$cachedUser' realizó la acción: -> $text")
    }
}
