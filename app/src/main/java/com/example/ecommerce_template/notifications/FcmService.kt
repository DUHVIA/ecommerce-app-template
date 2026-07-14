package com.example.ecommerce_template.notifications

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class FcmService : FirebaseMessagingService() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        AppNotificationManager.createNotificationChannel(applicationContext)
    }

    @Deprecated("FirebaseMessagingService.onNewToken está deprecado en la versión actual del SDK")
    override fun onNewToken(token: String) {
        @Suppress("DEPRECATION")
        super.onNewToken(token)
        Log.d(TAG, "Nuevo token FCM: $token")
        serviceScope.launch {
            FcmTokenStore(applicationContext).saveToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "Mensaje recibido de: ${message.from}")

        val notification = message.notification
        val data = message.data

        val title = notification?.title
            ?: data[KEY_TITLE]
            ?: getString(applicationInfo.labelRes)

        val body = notification?.body
            ?: data[KEY_BODY]
            ?: ""

        val targetRoute = data[KEY_ROUTE]

        AppNotificationManager.showNotification(
            context = applicationContext,
            title = title,
            body = body,
            route = targetRoute
        )
    }

    companion object {
        private const val TAG = "FcmService"

        const val KEY_TITLE = "title"
        const val KEY_BODY = "body"
        const val KEY_ROUTE = "route"
    }
}
