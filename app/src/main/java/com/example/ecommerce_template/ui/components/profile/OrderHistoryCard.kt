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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import com.example.ecommerce_template.ui.components.core.SecondaryOutlinedButton
// Asegúrate de tener tu IronCoreTheme si lo estás usando
// import com.example.ecommerce_template.ui.theme.IronCoreTheme

//SOLID APPLIED
// 1. Componente de Cabecera (SRP: Solo maneja estado, color del estado y fecha)
@Composable
fun OrderStatusHeader(
    status: String,
    isDelivered: Boolean,
    date: String,
    modifier: Modifier = Modifier
) {
    val statusColor = if (isDelivered) MaterialTheme.colorScheme.primary else Color(0xFF2DD4BF)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(color = statusColor, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = status.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = statusColor
            )
        }
        Text(
            text = date,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
    }
}

// 2. Componente de Información (SRP: Maneja la tipografía del ID y el resumen)
@Composable
fun OrderInfoContent(
    orderId: String,
    itemsSummary: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = orderId.uppercase(),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = itemsSummary,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray
        )
    }
}

// 3. Componente de Total (SRP: Maneja el formato del precio final)
@Composable
fun OrderTotalAmount(
    total: Double,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
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
}

// 4. Componente de Acciones (SRP: Agrupa los botones de esta tarjeta)
@Composable
fun OrderActionButtons(
    onReorderClick: () -> Unit,
    onDetailsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
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

// 5. Contenedor Base (OCP: Abierto a recibir distintos bloques a través de slots)
@Composable
fun OrderHistoryCardBase(
    modifier: Modifier = Modifier,
    headerSlot: @Composable () -> Unit,
    infoSlot: @Composable () -> Unit,
    totalSlot: @Composable () -> Unit,
    actionSlot: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            headerSlot()
            Spacer(modifier = Modifier.height(8.dp))

            infoSlot()
            Spacer(modifier = Modifier.height(16.dp))

            totalSlot()
            Spacer(modifier = Modifier.height(16.dp))

            actionSlot()
        }
    }
}

// 6. El Componente Final (Orquestador: Une la base con las piezas específicas)
@Composable
fun OrderHistoryCard(
    status: String,
    isDelivered: Boolean,
    date: String,
    orderId: String,
    itemsSummary: String,
    total: Double,
    onReorderClick: () -> Unit,
    onDetailsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OrderHistoryCardBase(
        modifier = modifier,
        headerSlot = {
            OrderStatusHeader(status = status, isDelivered = isDelivered, date = date)
        },
        infoSlot = {
            OrderInfoContent(orderId = orderId, itemsSummary = itemsSummary)
        },
        totalSlot = {
            OrderTotalAmount(total = total)
        },
        actionSlot = {
            OrderActionButtons(onReorderClick = onReorderClick, onDetailsClick = onDetailsClick)
        }
    )
}

// 7. Preview Integrado
@Preview(name = "Order History Preview", showBackground = true)
@Composable
fun OrderHistoryCardPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background // Ayuda a ver el contraste del borde
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Caso 1: Pedido entregado
                OrderHistoryCard(
                    status = "Delivered",
                    isDelivered = true,
                    date = "24 MAY 2026",
                    orderId = "#ORD-2026-98743",
                    itemsSummary = "Zapatillas Iron Runner Pro (x1), Medias Deportivas Pack x3 (x2)",
                    total = 159.99,
                    onReorderClick = {},
                    onDetailsClick = {}
                )

                // Caso 2: Pedido pendiente o en camino
                OrderHistoryCard(
                    status = "In Transit",
                    isDelivered = false,
                    date = "HOY, 11:30 AM",
                    orderId = "#ORD-2026-99102",
                    itemsSummary = "Camiseta Deportiva Transpirable (x1), Gorra Ajustable Iron (x1)",
                    total = 40.49,
                    onReorderClick = {},
                    onDetailsClick = {}
                )
            }
        }
    }
}