package com.example.ecommerce_template.data.product

import com.example.ecommerce_template.R
import com.example.ecommerce_template.data.local.dao.ProductDao
import com.example.ecommerce_template.data.local.entity.ProductEntity
import com.example.ecommerce_template.data.network.IronCoreApiService
import com.example.ecommerce_template.data.utils.SearchLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Repositorio de Productos implementando Offline-First (Single Source of Truth).
 */
class ProductRepository(
    private val apiService: IronCoreApiService,
    private val productDao: ProductDao,
    private val logger: SearchLogger
) {

    private val _isOfflineMode = MutableStateFlow(false)
    val isOfflineMode = _isOfflineMode.asStateFlow()

    /**
     * Única fuente de verdad: La base de datos local.
     * La UI observará este Flow. Si la base de datos está vacía al recolectar,
     * se disparará automáticamente una carga desde la API (refreshProducts).
     */
    val productsFlow: Flow<List<Product>> = productDao.getAllProductsFlow()
        .onEach { localProducts ->
            // Si la base de datos está vacía, disparamos la carga inicial
            if (localProducts.isEmpty()) {
                refreshProducts()
            }
        }
        .map { entities ->
            // Mapeamos de Entity (Room) a Modelo de Dominio (UI)
            entities.map { entity ->
                Product(
                    id = entity.id,
                    name = entity.name,
                    description = entity.description,
                    price = entity.price,
                    category = entity.category,
                    imageRes = entity.imageRes,
                    imageUrl = entity.imageUrl,
                    stock = entity.stock
                )
            }
        }

    /**
     * Descarga los productos desde la API y los guarda en la base de datos local.
     * Al guardarlos (upsertAll), Room notificará automáticamente al 'productsFlow',
     * actualizando la UI de forma reactiva sin necesidad de retornar los datos aquí.
     * 
     * Nota: Ejecutamos las operaciones en Dispatchers.IO ya que el DAO ya no tiene funciones suspend.
     */
    suspend fun refreshProducts(): Result<Unit> = withContext(Dispatchers.IO) {
        logger.saveSearch("Sincronización de Catálogo API a BD Local (Offline-First)")
        
        try {
            val response = apiService.getProducts(page = 1, size = 100)
            if (response.isSuccessful && response.body() != null) {
                val apiProducts = response.body()!!.items
                
                // Mapeamos de Modelo de Red (API) a Entidad (Room)
                val entities = apiProducts.map { apiProd ->
                    ProductEntity(
                        id = apiProd.id,
                        name = apiProd.name,
                        description = apiProd.description ?: "",
                        price = apiProd.price,
                        category = apiProd.categoryName,
                        imageRes = R.drawable.prod_shaker, // Placeholder o manejar luego
                        imageUrl = apiProd.imageUrl,
                        stock = apiProd.stock
                    )
                }
                
                // Guardamos en la base de datos (nuestra única fuente de verdad)
                productDao.upsertAll(entities)
                
                _isOfflineMode.value = false // Sincronización exitosa
                Result.success(Unit)
            } else {
                _isOfflineMode.value = true // Error en servidor, modo offline
                Result.failure(Exception("Error fetching products: ${response.code()}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _isOfflineMode.value = true // Sin internet o error de red, modo offline
            Result.failure(e)
        }
    }

    suspend fun getProductById(id: String): Result<Product?> {
        return Result.failure(NotImplementedError("Aún no implementado en offline-first"))
    }
}