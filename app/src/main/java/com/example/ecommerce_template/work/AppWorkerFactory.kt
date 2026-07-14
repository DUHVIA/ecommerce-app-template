package com.example.ecommerce_template.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.ecommerce_template.di.AppContainer

class AppWorkerFactory(private val container: AppContainer) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = when (workerClassName) {
        SyncCatalogWorker::class.java.name ->
            SyncCatalogWorker(appContext, workerParameters, container.productRepository)
        else -> null
    }
}
