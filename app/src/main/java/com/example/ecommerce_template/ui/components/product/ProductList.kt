package com.example.ecommerce_template.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Data class para representar la entidad real del negocio
data class Product(
    val id: String,
    val name: String,
    val price: Double
)

// Lista de prueba con el nicho de suplementos de gimnasio
val mockSupplements = listOf(
    Product("1", "Proteína Whey Aislada 2kg", 249.90),
    Product("2", "Creatina Monohidratada 500g", 89.90),
    Product("3", "Pre-Entreno Explosive 300g", 110.50),
    Product("4", "BCAA Aminoácidos 400g", 95.00),
    Product("5", "Cinturón de Levantamiento Cuero", 120.00)
)

@Composable
fun ProductList(
    productos: List<Product>,
    onAddToCart: (Product) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp)
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        items(productos) { producto ->
            ProductCard(
                nombre = producto.name,
                precio = producto.price,
                onAddClick = { onAddToCart(producto) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    MaterialTheme {
        ProductList(
            productos = mockSupplements,
            onAddToCart = { /* Acción de prueba */ }
        )
    }
}