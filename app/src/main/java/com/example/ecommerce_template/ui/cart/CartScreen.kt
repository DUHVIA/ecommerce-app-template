package com.example.ecommerce_template.ui.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerce_template.ui.components.cart.CartItemCard
import com.example.ecommerce_template.ui.components.core.EmptyState
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecommerce_template.ui.cart.CartEvent
import com.example.ecommerce_template.ui.cart.CartViewModel

@Composable
fun CartScreen(
    onNavigateToDetail: (String) -> Unit,
    onCheckoutClick: () -> Unit,
    onBrowseClick: () -> Unit,
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel
) {
    val uiState by cartViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        cartViewModel.refresh()
    }
    LaunchedEffect(Unit) {
        cartViewModel.events.collect { event ->
            when (event) {
                is CartEvent.Error -> snackbarHostState.showSnackbar(event.message)
                CartEvent.Cleared -> Unit
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (uiState.isLoading && uiState.items.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (uiState.isEmpty) {
            EmptyState(
                icon = Icons.Default.AddShoppingCart,
                title = "Arsenal Vacío",
                description = "Aún no has añadido productos a tu itinerario de entrenamiento. Explora el catálogo y empieza a cargar tu carrito.",
                action = {
                    PrimaryButton(
                        text = "Explorar Catálogo",
                        onClick = onBrowseClick
                    )
                }
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "MY ARSENAL",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(uiState.items, key = { it.id }) { item ->
                    CartItemCard(
                        item = item,
                        onIncrease = { cartViewModel.increase(item) },
                        onDecrease = { cartViewModel.decrease(item) },
                        onClick = { onNavigateToDetail(item.productId) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    OrderSummaryCard(
                        subtotal = uiState.subtotal,
                        taxes = 0.0,
                        shipping = 0.0,
                        total = uiState.subtotal,
                        isOnline = uiState.isOnline,
                        onCheckoutClick = onCheckoutClick
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) { data ->
            Snackbar(snackbarData = data)
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun OrderSummaryCard(
    subtotal: Double,
    taxes: Double,
    shipping: Double,
    total: Double,
    isOnline: Boolean,
    onCheckoutClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = "ORDER SUMMARY",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                Modifier,
                DividerDefaults.Thickness,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
            )
            Spacer(modifier = Modifier.height(16.dp))

            SummaryRow(label = "Subtotal", value = String.format("S/ %.2f", subtotal))
            Spacer(modifier = Modifier.height(12.dp))
            SummaryRow(label = "Shipping", value = "EN CHECKOUT")
            Spacer(modifier = Modifier.height(12.dp))
            SummaryRow(label = "Taxes", value = String.format("S/ %.2f", taxes))

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                Modifier,
                DividerDefaults.Thickness,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TOTAL",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
                )
                Text(
                    text = String.format("S/ %.2f", total),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = if (isOnline) "CHECKOUT NOW" else "OFFLINE — VUELVE CUANDO TENGAS RED",
                onClick = onCheckoutClick,
                enabled = isOnline,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Secure",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "SECURE CHECKOUT GUARANTEED",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge, color = Color.LightGray)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, color = Color.White)
    }
}
