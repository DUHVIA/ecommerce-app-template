package com.example.ecommerce_template

import android.app.Application
import com.example.ecommerce_template.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class IronCoreApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Arrancamos el motor de Inyección de Dependencias
        startKoin {
            // Le damos el contexto de Android
            androidContext(this@IronCoreApp)
            // Le pasamos nuestro manual de instrucciones
            modules(appModule)
        }
    }
}
