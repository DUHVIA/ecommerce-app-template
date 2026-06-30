package com.example.ecommerce_template.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope // <-- Esta es la importación clave
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

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        // Quitamos el 'androidx.lifecycle.' y dejamos solo viewModelScope
        viewModelScope.launch {
            val result = repository.getAllProducts()
            if (result.isSuccess) {
                _products.value = result.getOrNull() ?: emptyList()
            }
        }
    }

    fun getProductById(id: String): Product? {
        return products.value.find { it.id == id }
    }
}