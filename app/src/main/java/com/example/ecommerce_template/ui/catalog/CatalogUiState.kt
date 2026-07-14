package com.example.ecommerce_template.ui.catalog

import com.example.ecommerce_template.data.product.Product

sealed class SyncState {
    data object Idle : SyncState()
    data object Running : SyncState()
    data object Success : SyncState()
    data class Error(val message: String) : SyncState()
}

data class ProductListUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val isOnline: Boolean = true,
    val isOfflineBannerVisible: Boolean = false,
    val lastSyncAt: Long = 0L,
    val syncState: SyncState = SyncState.Idle
)

sealed class ProductEvent {
    data class Error(val message: String) : ProductEvent()
    data object Offline : ProductEvent()
}
