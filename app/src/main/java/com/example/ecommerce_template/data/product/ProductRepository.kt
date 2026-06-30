package com.example.ecommerce_template.data.product

import com.example.ecommerce_template.R
import com.example.ecommerce_template.data.network.IronCoreApiService
import com.example.ecommerce_template.data.utils.SearchLogger

class ProductRepository(
    private val apiService: IronCoreApiService,
    private val logger: SearchLogger
) {

    suspend fun getAllProducts(): Result<List<Product>> {
        logger.saveSearch("Consulta General de Catálogo API")
        
        return try {
            val response = apiService.getProducts(page = 1, size = 100)
            if (response.isSuccessful && response.body() != null) {
                val apiProducts = response.body()!!.items
                
                val domainProducts = apiProducts.map { apiProd ->
                    Product(
                        id = apiProd.id,
                        name = apiProd.name,
                        description = apiProd.description ?: "",
                        price = apiProd.price,
                        category = apiProd.categoryName,
                        imageRes = R.drawable.prod_shaker, // Placeholder
                        imageUrl = apiProd.imageUrl,
                        stock = apiProd.stock
                    )
                }
                Result.success(domainProducts)
            } else {
                Result.failure(Exception("Error fetching products: ${response.code()}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getProductById(id: String): Result<Product?> {
        // En una app real haríamos un GET /api/v1/products/{id}
        // Para este demo, reutilizamos el listado o asumimos que se llamará getAllProducts
        return Result.failure(NotImplementedError("Implement GET by ID api call if needed"))
    }
}