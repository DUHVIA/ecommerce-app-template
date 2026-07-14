package com.example.ecommerce_template

import android.app.Application
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import android.util.Log
import com.example.ecommerce_template.di.AppContainer
import com.example.ecommerce_template.notifications.AppNotificationManager
import com.example.ecommerce_template.notifications.FcmTokenStore
import com.example.ecommerce_template.work.AppWorkerFactory
import com.example.ecommerce_template.work.SyncCatalogWorker
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainApplication : Application() {

    lateinit var container: AppContainer
        private set

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(applicationContext)
        AppNotificationManager.createNotificationChannel(applicationContext)
        setupWorkManager()
        fetchAndStoreFcmToken()
    }

    private fun setupWorkManager() {
        val factory = AppWorkerFactory(container)
        val configuration = Configuration.Builder()
            .setWorkerFactory(factory)
            .build()
        WorkManager.initialize(this, configuration)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicRequest = PeriodicWorkRequestBuilder<SyncCatalogWorker>(12, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            SyncCatalogWorker.UNIQUE_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
    }

    @Suppress("DEPRECATION")
    private fun fetchAndStoreFcmToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "No se pudo obtener el token FCM", task.exception)
                    return@addOnCompleteListener
                }

                val token = task.result
                Log.d(TAG, "Token FCM: $token")
                applicationScope.launch {
                    FcmTokenStore(applicationContext).saveToken(token)
                }
            }
    }

    companion object {
        private const val TAG = "MainApplication"
    }
}
