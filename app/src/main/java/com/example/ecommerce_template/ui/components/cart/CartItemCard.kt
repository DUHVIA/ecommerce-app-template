package com.example.ecommerce_template.ui.components.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecommerce_template.R
import com.example.ecommerce_template.data.cart.CartItem

@Composable
fun CartItemCard(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
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
                    .clip(RoundedCornerShape(4.dp))
            ) {
                AsyncImage(
                    model = item.productImageUrl,
                    contentDescription = "Imagen de ${item.productName}",
                    placeholder = painterResource(R.drawable.product_placeholder),
                    error = painterResource(R.drawable.product_placeholder),
                    fallback = painterResource(R.drawable.product_placeholder),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.productName.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    lineHeight = 20.sp,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "S/ ${"%.2f".format(item.unitPrice)} c/u",
                    style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.5.sp),
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                CartItemActions(
                    quantity = item.quantity,
                    subtotal = item.subtotal,
                    onIncrease = onIncrease,
                    onDecrease = onDecrease,
                )
            }
        }
    }
}

@Composable
fun CartItemActions(
    quantity: Int,
    subtotal: Double,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        QuantitySelector(
            quantity = quantity,
            onIncrease = onIncrease,
            onDecrease = onDecrease
        )

        Text(
            text = "S/ ${"%.2f".format(subtotal)}",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onDecrease) {
            if (quantity <= 1) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = "Eliminar producto"
                )
            } else {
                Text(
                    text = "-",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

        Text(
            text = quantity.toString(),
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        IconButton(onClick = onIncrease) {
            Text(
                text = "+",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemCardPreview() {
    MaterialTheme {
        Surface {
            CartItemCard(
                item = CartItem(
                    id = "1",
                    productId = "p1",
                    productName = "ISO-CORE WHEY ELITE",
                    productImageUrl = null,
                    unitPrice = 79.99,
                    subtotal = 79.99,
                    quantity = 1
                ),
                onIncrease = {},
                onDecrease = {},
                onClick = {}
            )
        }
    }
}
