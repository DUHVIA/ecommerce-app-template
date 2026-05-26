package com.example.ecommerce_template.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecommerce_template.ui.components.core.IronSearchBar
import com.example.ecommerce_template.ui.components.product.CategoryChip
import com.example.ecommerce_template.ui.components.product.IronProductCard
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecommerce_template.ui.theme.IronCoreTheme

// --- MOCK DATA PARA VERLO FUNCIONAR ---
data class DummyProduct(val id: String, val category: String, val title: String, val price: Double, val badge: String?)

val trendingProducts = listOf(
    DummyProduct("1", "SUPPLEMENTS", "WHEY ISOLATE...", 59.99, "HOT"),
    DummyProduct("2", "EQUIPMENT", "HEX DUMBBELL...", 124.00, null),
    DummyProduct("3", "ACCESSORIES", "PRO LIFTING...", 24.99, null),
    DummyProduct("4", "ACCESSORIES", "TITANIUM...", 35.00, "ELITE"),
    DummyProduct("5", "PRE-WORKOUT", "IGNITE V2...", 44.99, "HOT"),
    DummyProduct("6", "RECOVERY", "BCAA MATRIX...", 32.99, null)
)

data class DummyCategory(val title: String, val icon: ImageVector)
val categories = listOf(
    DummyCategory("GEAR", Icons.Default.FitnessCenter),
    DummyCategory("PROTEINS", Icons.Default.LocalDrink),
    DummyCategory("APPAREL", Icons.Default.Checkroom)
)
// --------------------------------------

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetail: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    // Usamos LazyVerticalGrid para manejar la cabecera completa y los productos en 2 columnas
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // 1. Buscador (Ocupa las 2 columnas)
        item(span = { GridItemSpan(2) }) {
            IronSearchBar(
                value = searchQuery,
                onValueChange = { searchQuery = it }
            )
        }

        // 2. Banner Promocional (Ocupa las 2 columnas)
        item(span = { GridItemSpan(2) }) {
            PromoBanner()
        }

        // 3. Categorías (Ocupa las 2 columnas)
        item(span = { GridItemSpan(2) }) {
            Column {
                SectionHeader(title = "CATEGORIES", actionText = "VIEW ALL")
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(categories) { cat ->
                        CategoryChip(
                            title = cat.title,
                            icon = cat.icon,
                            onClick = { /* Filtrar categoría */ }
                        )
                    }
                }
            }
        }

        // 4. Título de Tendencias (Ocupa las 2 columnas)
        item(span = { GridItemSpan(2) }) {
            Spacer(modifier = Modifier.height(8.dp))
            SectionHeader(title = "TRENDING GEAR", actionText = null)
        }

        // 5. Lista de Productos (Se distribuyen automáticamente en las 2 columnas)
        items(trendingProducts) { product ->
            IronProductCard(
                badgeText = product.badge,
                category = product.category,
                title = product.title,
                price = product.price,
                onAddToCart = { /* Lógica de agregar al carrito */ }
            )
        }
    }
}

// Componente extra para el título de las secciones ("CATEGORIES", "TRENDING GEAR")
@Composable
fun SectionHeader(title: String, actionText: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
        )
        if (actionText != null) {
            Text(
                text = actionText,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }
    }
}

// Banner Promocional hardcodeado por ahora para simular el diseño
@Composable
fun PromoBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f) // Proporción rectangular
            .background(Color(0xFF161616), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "LIMITED TIME RELEASE",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "NEW WHEY\nPROTEIN",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "20% OFF ALL ORDERS",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    IronCoreTheme {
        HomeScreen()
    }
}