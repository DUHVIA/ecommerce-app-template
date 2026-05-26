package com.example.ecommerce_template.ui.components.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import com.example.ecommerce_template.ui.components.core.SecondaryOutlinedButton

@Composable
fun OrderHistoryCard(
    status: String, // Ej: "DELIVERED" o "IN TRANSIT"
    isDelivered: Boolean, // Para cambiar el color del indicador (Verde o Cian)
    date: String,
    orderId: String,
    itemsSummary: String,
    total: Double,
    onReorderClick: () -> Unit,
    onDetailsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant // Fondo tipo tarjeta
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Header: Estado y Fecha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(
                                color = if (isDelivered) MaterialTheme.colorScheme.primary else Color(0xFF2DD4BF), // Neón o Teal
                                shape = CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = status.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isDelivered) MaterialTheme.colorScheme.primary else Color(0xFF2DD4BF)
                    )
                }
                Text(
                    text = date,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Número de Orden
            Text(
                text = orderId.uppercase(),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Resumen de productos
            Text(
                text = itemsSummary,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Total
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "TOTAL",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$$total",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de acción reutilizados
            PrimaryButton(
                text = "REORDER",
                onClick = onReorderClick
            )
            Spacer(modifier = Modifier.height(8.dp))
            SecondaryOutlinedButton(
                text = "DETAILS",
                onClick = onDetailsClick
            )
        }
    }
}