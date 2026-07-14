package com.example.ecommerce_template.data.product

import com.example.ecommerce_template.core.ApiResult
import com.example.ecommerce_template.core.safeCall
import com.example.ecommerce_template.local.ProductDao
import com.example.ecommerce_template.local.toDomain
import com.example.ecommerce_template.local.toEntity
import com.example.ecommerce_template.network.NetworkMonitor
import com.example.ecommerce_template.network.api.CatalogApi
import com.example.ecommerce_template.network.mapper.Category
import com.example.ecommerce_template.network.mapper.toDomain
import com.example.ecommerce_template.core.SearchLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

sealed class RefreshOutcome {
    data object Synced : RefreshOutcome()
    data object Offline : RefreshOutcome()
    data class Error(val message: String) : RefreshOutcome()
}

class ProductRepository(
    private val catalogApi: CatalogApi,
    private val productDao: ProductDao,
    private val networkMonitor: NetworkMonitor,
    private val logger: SearchLogger
) {

    val products: Flow<List<Product>> = productDao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    val lastSyncAt: Flow<Long> = productDao.observeLastSyncAt()

    val isOnline: Flow<Boolean> = networkMonitor.observe()

    fun productByIdFlow(id: String): Flow<Product?> =
        productDao.observeById(id).map { it?.toDomain() }

    suspend fun refresh(): RefreshOutcome {
        val result = safeCall { catalogApi.products(size = 100) }
        return when (result) {
            is ApiResult.Success -> {
                val now = System.currentTimeMillis()
                val entities = result.data.items.map { it.toDomain().toEntity(now) }
                productDao.replaceAll(entities, now)
                logger.saveSearch("Consultó el catálogo completo de productos (${entities.size} ítems)")
                RefreshOutcome.Synced
            }
            is ApiResult.Error -> {
                if (result.httpStatus == null) RefreshOutcome.Offline else RefreshOutcome.Error(result.message)
            }
            ApiResult.Loading -> RefreshOutcome.Offline
        }
    }

    suspend fun refreshProductById(id: String): RefreshOutcome {
        val result = safeCall { catalogApi.productById(id) }
        return when (result) {
            is ApiResult.Success -> {
                val now = System.currentTimeMillis()
                productDao.upsertAll(listOf(result.data.toDomain().toEntity(now)))
                logger.saveSearch("Visualizó el producto: ${result.data.name}")
                RefreshOutcome.Synced
            }
            is ApiResult.Error -> {
                if (result.httpStatus == null) {
                    val cached = productDao.getById(id)
                    if (cached == null) RefreshOutcome.Offline else RefreshOutcome.Synced
                } else {
                    RefreshOutcome.Error(result.message)
                }
            }
            ApiResult.Loading -> RefreshOutcome.Offline
        }
    }

    suspend fun getCategories(): ApiResult<List<Category>> =
        safeCall { catalogApi.categories() }.map { list -> list.map { it.toDomain() } }

    fun observeCategoriesAndProducts() =
        combine(products, productDao.observeLastSyncAt()) { list, sync -> list to sync }
}
