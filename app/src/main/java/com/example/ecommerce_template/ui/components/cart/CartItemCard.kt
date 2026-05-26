package com.example.ecommerce_template.ui.components.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp

@Composable
fun CartItemCard(
    category: String,
    title: String,
    variantDetails: String,
    price: Double,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background // Negro profundo para el carrito
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = variantDetails.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = 0.5.sp
                    ),
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Control de Cantidad
                    Row(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "-",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .clickable { onDecrease() }
                                .padding(horizontal = 8.dp)
                        )
                        Text(
                            text = quantity.toString(),
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                        Text(
                            text = "+",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .clickable { onIncrease() }
                                .padding(horizontal = 8.dp)
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "$$price",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = "REMOVE",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = Color(0xFFFF5252), // Color coral/rojo suave
                            modifier = Modifier
                                .clickable { onRemove() }
                                .padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Cart Item Preview", showBackground = true)
@Composable
fun CartItemCardPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CartItemCard(
                    category = "Calzado",
                    title = "Zapatillas Iron Runner Pro",
                    variantDetails = "Talla: 42 | Color: Negro",
                    price = 129.99,
                    quantity = 1,
                    onIncrease = {},
                    onDecrease = {},
                    onRemove = {},
                    onClick = {}
                )

                CartItemCard(
                    category = "Accesorios",
                    title = "Medias Deportivas Pack x3",
                    variantDetails = "Talla: Única | Color: Blanco",
                    price = 15.00,
                    quantity = 12,
                    onIncrease = {},
                    onDecrease = {},
                    onRemove = {},
                    onClick = {}
                )
            }
        }
    }
}