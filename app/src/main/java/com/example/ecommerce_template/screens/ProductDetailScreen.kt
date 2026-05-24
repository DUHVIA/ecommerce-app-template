package com.example.ecommerce_template.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.ecommerce_template.ui.components.product.ProductList
import com.example.ecommerce_template.ui.components.product.mockSupplements

@Composable
fun ProductDetailScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        /*Text(
            text = "Product Detail",
            textAlign = TextAlign.Center
        )*/

        ProductList(
            productos = mockSupplements,
            onAddToCart = { /* Acción de prueba */ }
        )
    }
}