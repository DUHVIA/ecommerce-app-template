package com.example.ecommerce_template.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.ecommerce_template.data.product.ProductRepository
import com.example.ecommerce_template.data.product.RefreshOutcome

class SyncCatalogWorker(
    appContext: Context,
    params: WorkerParameters,
    private val productRepository: ProductRepository
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return when (val outcome = productRepository.refresh()) {
            RefreshOutcome.Synced -> Result.success()
            RefreshOutcome.Offline ->
                if (runAttemptCount < MAX_RETRIES) Result.retry() else Result.success()
            is RefreshOutcome.Error ->
                if (runAttemptCount < MAX_RETRIES) Result.retry() else Result.failure(
                    workDataOf(KEY_ERROR to outcome.message)
                )
        }
    }

    companion object {
        const val UNIQUE_NAME = "sync_catalog_periodic"
        const val ONE_TIME_WORK_NAME = "sync_catalog_on_demand"
        const val KEY_ERROR = "error"
        private const val MAX_RETRIES = 3
    }
}
