package com.example.ecommerce_template.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecommerce_template.ui.components.profile.OrderHistoryCard
import com.example.ecommerce_template.ui.theme.IronCoreTheme
import com.example.ecommerce_template.ui.viewModel.OrderHistoryViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PurchaseHistoryScreen(
    modifier: Modifier = Modifier,
    orderHistoryViewModel: OrderHistoryViewModel = viewModel()
) {
    val uiState by orderHistoryViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isEmpty) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingBag,
                contentDescription = "Sin ordenes",
                modifier = Modifier
                    .size(96.dp)
                    .padding(bottom = 16.dp),
                tint = Color.Gray
            )

            Text(
                text = "No tienes ordenes.",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Black),
                color = Color.Gray
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
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
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(uiState.orders) { order ->
                OrderHistoryCard(
                    order = order,
                    onReorderClick = {},
                    onDetailsClick = {}
                )
            }

            // Botón "LOAD OLDER ORDERS" al final
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Cargar más historial */ }
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LOAD OLDER ORDERS",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Load more",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PurchaseHistoryScreenPreview() {
    IronCoreTheme {
        PurchaseHistoryScreen()
    }
}