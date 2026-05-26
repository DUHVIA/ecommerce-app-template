package com.example.ecommerce_template.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerce_template.ui.components.cart.CartItemCard
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecommerce_template.ui.theme.IronCoreTheme

// --- MOCK DATA PARA EL CARRITO ---
data class DummyCartItem(
    val id: String,
    val category: String,
    val title: String,
    val variantDetails: String,
    val price: Double,
    var quantity: Int
)

val initialCartItems = listOf(
    DummyCartItem("1", "PROTEIN", "ISO-CORE WHEY ELITE", "FLAVOR: DARK CHOCOLATE / 2.2KG", 79.99, 1),
    DummyCartItem("2", "EQUIPMENT", "CORE-TECH PRO BELT", "SIZE: LARGE / MATTE BLACK", 124.50, 1),
    DummyCartItem("3", "ENERGY", "IGNITION PRE-MAX", "FLAVOR: ELECTRIC LIME / 40 SERVINGS", 45.00, 1)
)
// ---------------------------------

@Composable
fun CartScreen(
    onNavigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Estado reactivo para la lista (permite simular que sumamos o restamos cantidades)
    val cartItems = remember { mutableStateListOf(*initialCartItems.toTypedArray()) }

    val subtotal = cartItems.sumOf { it.price * it.quantity }
    val taxes = subtotal * 0.08 // Simulación de 8% de impuestos
    val total = subtotal + taxes

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Cabecera "MY ARSENAL"
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
                Text(
                    text = "CONTINUE SHOPPING ->",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Lista de Productos en el carrito
        items(cartItems) { item ->
            CartItemCard(
                category = item.category,
                title = item.title,
                variantDetails = item.variantDetails,
                price = item.price,
                quantity = item.quantity,
                onIncrease = {
                    val index = cartItems.indexOf(item)
                    if (index != -1) cartItems[index] = item.copy(quantity = item.quantity + 1)
                },
                onDecrease = {
                    val index = cartItems.indexOf(item)
                    if (index != -1 && item.quantity > 1) {
                        cartItems[index] = item.copy(quantity = item.quantity - 1)
                    }
                },
                onRemove = { cartItems.remove(item) },
                onClick = { onNavigateToDetail(item.id) }
            )
        }

        // Resumen de la Orden (Order Summary)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            OrderSummaryCard(
                subtotal = subtotal,
                taxes = taxes,
                total = total,
                onCheckoutClick = { /* Navegar al flujo de pago */ }
            )
        }
    }
}

@Composable
fun OrderSummaryCard(
    subtotal: Double,
    taxes: Double,
    total: Double,
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
            Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(16.dp))

            // Subtotal
            SummaryRow(label = "Subtotal", value = String.format("$%.2f", subtotal))
            Spacer(modifier = Modifier.height(12.dp))

            // Shipping
            SummaryRow(label = "Shipping", value = "Calculated at checkout")
            Spacer(modifier = Modifier.height(12.dp))

            // Taxes
            SummaryRow(label = "Taxes", value = String.format("$%.2f", taxes))

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(16.dp))

            // Total
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
                    text = String.format("$%.2f", total),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de Checkout con Ícono
            PrimaryButton(
                text = "CHECKOUT NOW",
                onClick = onCheckoutClick,
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CartScreenPreview() {
    IronCoreTheme {
        CartScreen(onNavigateToDetail = {})
    }
}