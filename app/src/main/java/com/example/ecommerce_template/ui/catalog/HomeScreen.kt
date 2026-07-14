package com.example.ecommerce_template.ui.catalog

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecommerce_template.ui.components.core.SearchBar
import com.example.ecommerce_template.ui.components.core.OfflineBanner
import com.example.ecommerce_template.ui.components.product.ProductCard
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecommerce_template.ui.catalog.ProductEvent
import com.example.ecommerce_template.ui.catalog.ProductViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    productViewModel: ProductViewModel,
    onNavigateToDetail: (String) -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    val uiState by productViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        productViewModel.refresh()
    }
    LaunchedEffect(Unit) {
        productViewModel.events.collect { event ->
            when (event) {
                is ProductEvent.Error -> snackbarHostState.showSnackbar(event.message)
                ProductEvent.Offline -> {
                    if (uiState.products.isEmpty()) {
                        snackbarHostState.showSnackbar("Sin conexión — mostrando caché si está disponible")
                    }
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        if (uiState.products.isEmpty()) {
            EmptyOrLoadingState(
                isLoading = uiState.isLoading,
                isOnline = uiState.isOnline,
                onRetry = { productViewModel.refresh() }
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(span = { GridItemSpan(2) }) {
                    OfflineBanner(
                        visible = uiState.isOfflineBannerVisible,
                        lastSyncAt = uiState.lastSyncAt,
                        isSyncing = uiState.syncState is com.example.ecommerce_template.ui.catalog.SyncState.Running,
                        onSyncClick = { productViewModel.requestManualSync() }
                    )
                }

                item(span = { GridItemSpan(2) }) {
                    SearchBar(
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

                items(uiState.products, key = { it.id }) { item ->
                    ProductCard(
                        item = item,
                        badgeText = null,
                        onAddToCart = {
                            productViewModel.addToCart(item.id)
                        },
                        onClick = {
                            onNavigateToDetail(item.id)
                        }
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) { data -> Snackbar(snackbarData = data) }
    }
}

@Composable
private fun EmptyOrLoadingState(
    isLoading: Boolean,
    isOnline: Boolean,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Cargando catálogo…",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        } else {
            Text(
                text = if (isOnline) "No hay productos para mostrar" else "Sin conexión y sin caché",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.primary
            ) {
                TextButton(onClick = onRetry) {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = "REINTENTAR",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

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

@Composable
fun PromoBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f)
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
