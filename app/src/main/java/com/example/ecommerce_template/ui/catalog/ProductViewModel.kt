package com.example.ecommerce_template.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.ecommerce_template.data.cart.CartRepository
import com.example.ecommerce_template.data.product.Product
import com.example.ecommerce_template.data.product.ProductRepository
import com.example.ecommerce_template.data.product.RefreshOutcome
import com.example.ecommerce_template.work.SyncCatalogWorker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val workManager: WorkManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    private val _events = Channel<ProductEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            productRepository.products.collect { products ->
                _uiState.update { it.copy(products = products) }
            }
        }
        viewModelScope.launch {
            productRepository.lastSyncAt.collect { sync ->
                _uiState.update { current ->
                    current.copy(
                        lastSyncAt = sync,
                        isOfflineBannerVisible = !current.isOnline ||
                            (sync > 0L && (System.currentTimeMillis() - sync) > STALE_THRESHOLD_MS)
                    )
                }
            }
        }
        viewModelScope.launch {
            productRepository.isOnline.collect { online ->
                _uiState.update { current ->
                    current.copy(
                        isOnline = online,
                        isOfflineBannerVisible = !online ||
                            (current.lastSyncAt > 0L &&
                                (System.currentTimeMillis() - current.lastSyncAt) > STALE_THRESHOLD_MS)
                    )
                }
            }
        }
        viewModelScope.launch { refresh() }
        observeWorkInfo()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val outcome = productRepository.refresh()
            _uiState.update { it.copy(isLoading = false) }
            when (outcome) {
                is RefreshOutcome.Error -> _events.trySend(ProductEvent.Error(outcome.message))
                is RefreshOutcome.Offline -> _events.trySend(ProductEvent.Offline)
                RefreshOutcome.Synced -> Unit
            }
        }
    }

    fun loadProductById(id: String) {
        viewModelScope.launch {
            productRepository.productByIdFlow(id).collect { p ->
                _selectedProduct.value = p
            }
        }
        viewModelScope.launch {
            productRepository.refreshProductById(id)
        }
    }

    fun addToCart(productId: String) {
        viewModelScope.launch {
            cartRepository.add(productId, 1)
        }
    }

    fun requestManualSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<SyncCatalogWorker>()
            .setConstraints(constraints)
            .addTag(SyncCatalogWorker.ONE_TIME_WORK_NAME)
            .build()

        workManager.enqueueUniqueWork(
            SyncCatalogWorker.ONE_TIME_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    private fun observeWorkInfo() {
        viewModelScope.launch {
            mergeWorkStates(
                workManager.getWorkInfosForUniqueWorkFlow(SyncCatalogWorker.UNIQUE_NAME),
                workManager.getWorkInfosForUniqueWorkFlow(SyncCatalogWorker.ONE_TIME_WORK_NAME)
            ).collect { state ->
                _uiState.update { it.copy(syncState = state) }
            }
        }
    }

    private fun mergeWorkStates(
        periodic: Flow<List<WorkInfo>>,
        onDemand: Flow<List<WorkInfo>>
    ): Flow<SyncState> {
        return kotlinx.coroutines.flow.combine(periodic, onDemand) { periodicInfos, onDemandInfos ->
            val periodicState = periodicInfos.firstOrNull()?.state
            val onDemandState = onDemandInfos.firstOrNull()?.state
            val effectiveState = listOfNotNull(periodicState, onDemandState)
                .maxByOrNull { priority(it) }
            mapWorkState(effectiveState)
        }
    }

    private fun priority(state: WorkInfo.State): Int = when (state) {
        WorkInfo.State.RUNNING -> 4
        WorkInfo.State.ENQUEUED -> 3
        WorkInfo.State.SUCCEEDED -> 2
        WorkInfo.State.FAILED -> 1
        else -> 0
    }

    private fun mapWorkState(state: WorkInfo.State?): SyncState = when (state) {
        WorkInfo.State.ENQUEUED,
        WorkInfo.State.RUNNING -> SyncState.Running
        WorkInfo.State.SUCCEEDED -> SyncState.Success
        WorkInfo.State.FAILED -> SyncState.Error("No se pudo sincronizar el catálogo")
        WorkInfo.State.BLOCKED,
        WorkInfo.State.CANCELLED,
        null -> SyncState.Idle
    }

    companion object {
        private const val STALE_THRESHOLD_MS = 15 * 1000L
    }
}
