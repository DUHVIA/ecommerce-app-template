package com.example.ecommerce_template.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerce_template.ui.components.profile.OrderHistoryCard
import com.example.ecommerce_template.ui.theme.IronCoreTheme

// --- MOCK DATA PARA EL HISTORIAL ---
data class DummyOrder(
    val status: String,
    val isDelivered: Boolean,
    val date: String,
    val orderId: String,
    val itemsSummary: String,
    val total: Double
)

val orderHistoryList = listOf(
    DummyOrder(
        status = "DELIVERED",
        isDelivered = true,
        date = "May 12, 2024",
        orderId = "ORD-882941",
        itemsSummary = "Whey Isolate (Vanilla), Pre-Workout Ignition, Shaker Pro.",
        total = 189.50
    ),
    DummyOrder(
        status = "IN TRANSIT",
        isDelivered = false,
        date = "Today",
        orderId = "ORD-901242",
        itemsSummary = "Core Stability Mat, Weighted Vest (10kg), Knee Sleeves.",
        total = 342.10
    ),
    DummyOrder(
        status = "DELIVERED",
        isDelivered = true,
        date = "April 28, 2024",
        orderId = "ORD-771032",
        itemsSummary = "Hex Dumbbell Pair (25kg), Chalk Block 2-Pack.",
        total = 112.00
    )
)
// ------------------------------------

@Composable
fun PurchaseHistoryScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Cabecera "ORDER HISTORY"
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

                // Botón de filtro "LAST 6 MONTHS"
                Card(
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    modifier = Modifier.clickable { /* Mostrar opciones de filtro */ }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "LAST 6 MONTHS",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Lista de Órdenes
        items(orderHistoryList) { order ->
            OrderHistoryCard(
                status = order.status,
                isDelivered = order.isDelivered,
                date = order.date,
                orderId = order.orderId,
                itemsSummary = order.itemsSummary,
                total = order.total,
                onReorderClick = { /* Lógica para volver a pedir o rastrear */ },
                onDetailsClick = { /* Navegar a detalle de orden */ }
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PurchaseHistoryScreenPreview() {
    IronCoreTheme {
        PurchaseHistoryScreen()
    }
}