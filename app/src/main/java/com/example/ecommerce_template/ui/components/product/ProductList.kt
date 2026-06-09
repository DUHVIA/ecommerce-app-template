package com.example.ecommerce_template.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerce_template.ui.theme.IronCoreTheme

data class Product(
    val id: String,
    val name: String,
    val price: Double
)

val mockSupplements = listOf(
    Product("1", "Proteína Whey Aislada 2kg", 249.90),
    Product("2", "Creatina Monohidratada 500g", 89.90),
    Product("3", "Pre-Entreno Explosive 300g", 110.50),
    Product("4", "BCAA Aminoácidos 400g", 95.00),
    Product("5", "Cinturón de Levantamiento Cuero", 120.00)
)

//SOLID APPLIED
@Composable
fun <T> IronListBase(
    items: List<T>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp),
    itemContent: @Composable (T) -> Unit // Slot: delega cómo se dibuja cada ítem
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = verticalArrangement
    ) {
        items(items) { item ->
            itemContent(item)
        }
    }
}

@Composable
fun ProductList(
    productos: List<Product>,
    onAddToCart: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    IronListBase(
        items = productos,
        modifier = modifier
    ) { producto ->
        // Aquí le decimos a la lista base CÓMO debe dibujar cada 'Product'
        ProductCard(
            nombre = producto.name,
            precio = producto.price,
            onAddClick = { onAddToCart(producto) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    IronCoreTheme {
        // Envolvemos en Surface para que tome el color de fondo oscuro de IronCoreTheme
        Surface(color = MaterialTheme.colorScheme.background) {
            ProductList(
                productos = mockSupplements,
                onAddToCart = { /* Acción de prueba */ }
            )
        }
    }
}