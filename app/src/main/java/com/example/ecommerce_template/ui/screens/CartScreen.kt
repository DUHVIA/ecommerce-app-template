package com.example.ecommerce_template.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerce_template.ui.components.cart.CartItemCard
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecommerce_template.ui.theme.IronCoreTheme
import com.example.ecommerce_template.ui.viewModel.CartViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(
    onNavigateToDetail: (String) -> Unit,
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel = koinViewModel()
) {

    val uiState by cartViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isEmpty) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Default.AddShoppingCart,
                contentDescription = "Carrito vacío",
                modifier = Modifier
                    .size(96.dp)
                    .padding(bottom = 16.dp),
                tint = Color.Gray
            )

            Text(
                text = "Tu carrito está vacío,\n compra algo ...",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Black
                ),
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {

                    Text(
                        text = "MY ARSENAL",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Black
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            items(uiState.items) { item ->

                CartItemCard(
                    item = item,

                    onIncrease = {
                        cartViewModel.addProduct(item.product)
                    },

                    onDecrease = {
                        cartViewModel.removeProduct(
                            item.product.id
                        )
                    },

                    onClick = {
                        onNavigateToDetail(
                            item.product.id.toString()
                        )
                    }
                )
            }

            item {

                Spacer(modifier = Modifier.height(16.dp))

                OrderSummaryCard(
                    subtotal = uiState.subtotal,
                    taxes = uiState.taxes,
                    total = uiState.total,
                    onCheckoutClick = onCheckoutClick
                )
            }
        }
    }
}

@SuppressLint("DefaultLocale")
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
            HorizontalDivider(
                Modifier,
                DividerDefaults.Thickness,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Subtotal
            SummaryRow(label = "Subtotal", value = String.format("$%.2f", subtotal))
            Spacer(modifier = Modifier.height(12.dp))

            // Shipping
            SummaryRow(label = "Shipping", value = "EN CHECKOUT")
            Spacer(modifier = Modifier.height(12.dp))

            // Taxes
            SummaryRow(label = "Taxes", value = String.format("$%.2f", taxes))

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                Modifier,
                DividerDefaults.Thickness,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
            )
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
        CartScreen(
            onNavigateToDetail = {},
            onCheckoutClick =  {}
        )
    }
}