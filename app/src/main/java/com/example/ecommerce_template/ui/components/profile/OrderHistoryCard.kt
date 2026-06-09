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
import com.example.ecommerce_template.data.checkOut.CheckoutSummary
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import com.example.ecommerce_template.ui.components.core.SecondaryOutlinedButton

@SuppressLint("DefaultLocale")
@Composable
fun OrderHistoryCard(
    order: CheckoutSummary,
    onReorderClick: () -> Unit,
    onDetailsClick: () -> Unit,
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
                text = "#ORD-000X",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = order.items.joinToString(),
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
                    text = String.format("$%.2f", order.total),
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

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

@Preview(showBackground = true)
@Composable
fun PreviewOrderHistoryCard() {
    val testOrder = CheckoutSummary(
        items = listOf("(x2) Camiseta Arsenal", "(x1) Balón Oficial"),
        subtotal = 150.0,
        taxes = 27.0,
        shipping = 10.0,
        total = 187.0
    )

    OrderHistoryCard(
        order = testOrder,
        onReorderClick = {},
        onDetailsClick = {}
    )
}