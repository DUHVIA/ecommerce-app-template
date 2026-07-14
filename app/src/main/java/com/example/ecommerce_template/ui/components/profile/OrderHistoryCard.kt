package com.example.ecommerce_template.ui.components.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerce_template.data.address.Address
import com.example.ecommerce_template.data.order.Order
import com.example.ecommerce_template.data.order.OrderItem
import com.example.ecommerce_template.data.order.OrderStatus
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import com.example.ecommerce_template.ui.components.core.SecondaryOutlinedButton
import com.example.ecommerce_template.ui.theme.IronCoreTheme

@SuppressLint("DefaultLocale")
@Composable
fun OrderHistoryCard(
    order: Order,
    onCancelClick: () -> Unit = {},
    onReorderClick: () -> Unit = {},
    onDetailsClick: () -> Unit = {},
    modifier: Modifier = Modifier
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
            Text(
                text = "#${order.id.take(8).uppercase()}",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            AssistChip(
                onClick = {},
                enabled = false,
                label = { Text(order.rawStatus.uppercase()) },
                colors = AssistChipDefaults.assistChipColors(
                    disabledContainerColor = order.status.color().copy(alpha = 0.2f),
                    disabledLabelColor = order.status.color()
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = order.items.joinToString(separator = "\n") { "(x${it.quantity}) ${it.productName}" },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "TOTAL",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = String.format("S/ %.2f", order.total),
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (order.status == OrderStatus.PENDING || order.status == OrderStatus.PAID) {
                SecondaryOutlinedButton(
                    text = "CANCEL ORDER",
                    onClick = onCancelClick
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
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

private fun OrderStatus.color(): Color = when (this) {
    OrderStatus.PENDING -> Color(0xFFFFA000)
    OrderStatus.PAID -> Color(0xFF1976D2)
    OrderStatus.PROCESSING -> Color(0xFF7B1FA2)
    OrderStatus.SHIPPED -> Color(0xFF388E3C)
    OrderStatus.DELIVERED -> Color(0xFF2E7D32)
    OrderStatus.CANCELLED -> Color(0xFFD32F2F)
    OrderStatus.UNKNOWN -> Color.Gray
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderHistoryCard() {
    IronCoreTheme {
        OrderHistoryCard(
            order = Order(
                id = "ord-0001-uuid-test",
                status = OrderStatus.PENDING,
                rawStatus = "pending",
                shippingAddress = Address(
                    id = "addr-1",
                    street = "Av. Venezuela s/n",
                    city = "Arequipa",
                    state = "Arequipa",
                    zipCode = "04001",
                    isDefault = true
                ),
                subtotal = 150.0,
                taxes = 27.0,
                shippingCost = 10.0,
                total = 187.0,
                items = listOf(
                    OrderItem(
                        id = "oi-1",
                        productId = "p-1",
                        productName = "Camiseta Arsenal",
                        quantity = 2,
                        unitPrice = 50.0,
                        subtotal = 100.0
                    ),
                    OrderItem(
                        id = "oi-2",
                        productId = "p-2",
                        productName = "Balón Oficial",
                        quantity = 1,
                        unitPrice = 50.0,
                        subtotal = 50.0
                    )
                ),
                createdAt = "2025-01-01T00:00:00"
            )
        )
    }
}
