package com.example.ecommerce_template.ui.catalog

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.ecommerce_template.R
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import com.example.ecommerce_template.ui.catalog.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onAddToCart: (String) -> Unit = {},
    productViewModel: ProductViewModel
) {
    val product by productViewModel.selectedProduct.collectAsStateWithLifecycle()
    val uiState by productViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(productId) {
        productViewModel.loadProductById(productId)
    }
    LaunchedEffect(Unit) {
        productViewModel.events.collect { event ->
            when (event) {
                is com.example.ecommerce_template.ui.catalog.ProductEvent.Error ->
                    snackbarHostState.showSnackbar(event.message)
                com.example.ecommerce_template.ui.catalog.ProductEvent.Offline -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "IRON CORE",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (product == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Outlined.CloudOff,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(56.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Producto no disponible sin conexión",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                        }
                    }
                }
            } else {
                val p = product!!
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    RoundedCornerShape(8.dp)
                                )
                        ) {
                            AsyncImage(
                                model = p.imageUrl,
                                contentDescription = "Imagen de ${p.name}",
                                placeholder = painterResource(R.drawable.product_placeholder),
                                error = painterResource(R.drawable.product_placeholder),
                                fallback = painterResource(R.drawable.product_placeholder),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        Text(
                            text = p.categoryName.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = p.name.uppercase(),
                            style = MaterialTheme.typography.headlineLarge.copy(lineHeight = 36.sp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "S/ ${"%.2f".format(p.price)}",
                                style = MaterialTheme.typography.headlineLarge
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Stock: ${p.stock}",
                            style = MaterialTheme.typography.labelLarge,
                            color = if (p.stock > 0) MaterialTheme.colorScheme.primary else Color.Red
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(modifier = Modifier.padding(24.dp)) {
                                Text(
                                    text = "DESCRIPCIÓN",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = p.description.ifBlank { "Sin descripción" },
                                    style = MaterialTheme.typography.bodyLarge,
                                    lineHeight = 24.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    item {
                        val canAdd = p.stock > 0 && uiState.isOnline
                        PrimaryButton(
                            text = when {
                                !uiState.isOnline -> "OFFLINE — VUELVE CUANDO TENGAS RED"
                                p.stock <= 0 -> "SIN STOCK"
                                else -> "AÑADIR AL CARRITO"
                            },
                            onClick = { if (canAdd) onAddToCart(p.id) },
                            enabled = canAdd,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AddShoppingCart,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
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
}
