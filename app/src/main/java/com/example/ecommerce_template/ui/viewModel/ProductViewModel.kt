package com.example.ecommerce_template.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.ecommerce_template.data.product.Product
import com.example.ecommerce_template.data.product.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductViewModel : ViewModel() {

    private val _products = MutableStateFlow(
        ProductRepository.getAllProducts()
    )

    val products = _products.asStateFlow()

    fun getProductById(id: Int): Product? {
        return products.value.find { it.id == id }
    }
}