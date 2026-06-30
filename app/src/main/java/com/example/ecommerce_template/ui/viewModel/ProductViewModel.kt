package com.example.ecommerce_template.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_template.data.product.Product
import com.example.ecommerce_template.data.product.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Modificamos el constructor para recibir el repositorio inyectado por Koin
class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    val isOfflineMode = repository.isOfflineMode

    init {
        // En Offline-First, simplemente observamos el Flow del repositorio.
        // El repositorio se encargará automáticamente de cargar de la API si la BD está vacía.
        viewModelScope.launch {
            repository.productsFlow.collect { productList ->
                _products.value = productList
            }
        }
    }

    // Opcional: si la UI tiene un "Pull to refresh" (jalar para actualizar)
    fun forceRefresh() {
        viewModelScope.launch {
            repository.refreshProducts()
        }
    }

    fun getProductById(id: String): Product? {
        return products.value.find { it.id == id }
    }
}