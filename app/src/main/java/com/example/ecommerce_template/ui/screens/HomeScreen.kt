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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecommerce_template.ui.components.core.IronSearchBar
import com.example.ecommerce_template.ui.components.product.IronProductCard
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecommerce_template.data.cart.CartRepository
import com.example.ecommerce_template.ui.theme.IronCoreTheme
import com.example.ecommerce_template.ui.viewModel.ProductViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetail: (String) -> Unit = {},
    productViewModel: ProductViewModel = viewModel()
) {

    var searchQuery by remember { mutableStateOf("") }

    val products by productViewModel.products.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item(span = { GridItemSpan(2) }) {
            IronSearchBar(
                value = searchQuery,
                onValueChange = { searchQuery = it }
            )
        }

        item(span = { GridItemSpan(2) }) {
            PromoBanner()
        }

        item(span = { GridItemSpan(2) }) {
            Spacer(modifier = Modifier.height(8.dp))
            SectionHeader(
                title = "TRENDING GEAR",
                actionText = null
            )
        }

        items(products) { item ->

            IronProductCard(
                item = item,
                badgeText = null,
                onAddToCart = {
                    CartRepository.addProductToCart(item)
                },
                onClick = {
                    onNavigateToDetail(item.id.toString())
                }
            )

        }
    }
}
// Componente extra para el título de las secciones
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


/*
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
*/