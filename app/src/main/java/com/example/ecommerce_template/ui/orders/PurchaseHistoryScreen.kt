package com.example.ecommerce_template.ui.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecommerce_template.ui.components.core.EmptyState
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import com.example.ecommerce_template.ui.components.profile.OrderHistoryCard
import com.example.ecommerce_template.ui.orders.OrderHistoryViewModel

@Composable
fun PurchaseHistoryScreen(
    onBrowseClick: () -> Unit,
    modifier: Modifier = Modifier,
    orderHistoryViewModel: OrderHistoryViewModel
) {
    val uiState by orderHistoryViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        val msg = uiState.errorMessage
        if (!msg.isNullOrBlank()) {
            snackbarHostState.showSnackbar(msg)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (uiState.isLoading && uiState.orders.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (uiState.isEmpty) {
            EmptyState(
                icon = Icons.Default.ShoppingBag,
                title = "Sin Registros",
                description = "Aún no has realizado ninguna orden. Cuando completes tu primera compra, tu historial de rendimiento aparecerá aquí.",
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
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "PERFORMANCE RECORDS",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "ORDER HISTORY",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Black
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                items(uiState.orders, key = { it.id }) { order ->
                    OrderHistoryCard(
                        order = order,
                        onCancelClick = { orderHistoryViewModel.cancel(order) },
                        onReorderClick = { },
                        onDetailsClick = { }
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
