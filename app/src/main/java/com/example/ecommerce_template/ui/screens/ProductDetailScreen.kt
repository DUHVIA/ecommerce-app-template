package com.example.ecommerce_template.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import com.example.ecommerce_template.ui.theme.IronCoreTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onCartClick: () -> Unit = {}
) {
    var quantity by remember { mutableStateOf(1) }
    var selectedWeight by remember { mutableStateOf("2.0 LBS") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("IRON CORE", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = onCartClick) {
                        Icon(Icons.Outlined.ShoppingCart, contentDescription = "Cart", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Imagen principal simulada con un Box
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = "NEW FORMULA",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.Black),
                        modifier = Modifier
                            .padding(16.dp)
                            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(2.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Textos descriptivos y precio
            item {
                Text(
                    text = "PERFORMANCE RANGE",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ISO-PRO WHEY\nISOLATE",
                    style = MaterialTheme.typography.headlineLarge.copy(lineHeight = 36.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$59.99",
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$74.99",
                        style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough),
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Selector de peso
            item {
                Text(text = "SELECT WEIGHT", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    WeightSelectorCard(
                        weight = "2.0 LBS",
                        servings = "28 Servings",
                        isSelected = selectedWeight == "2.0 LBS",
                        onClick = { selectedWeight = "2.0 LBS" },
                        modifier = Modifier.weight(1f)
                    )
                    WeightSelectorCard(
                        weight = "5.0 LBS",
                        servings = "72 Servings",
                        isSelected = selectedWeight == "5.0 LBS",
                        onClick = { selectedWeight = "5.0 LBS" },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Tabla nutricional (Macros)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MacroItem(label = "PROTEIN", value = "25G")
                    Divider(modifier = Modifier.height(40.dp).width(1.dp), color = Color.DarkGray)
                    MacroItem(label = "BCAA", value = "5.5G")
                    Divider(modifier = Modifier.height(40.dp).width(1.dp), color = Color.DarkGray)
                    MacroItem(label = "SUGAR", value = "0G")
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Controles de cantidad y botón añadir al carrito
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botones de cantidad
                    Row(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "-",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.clickable { if (quantity > 1) quantity-- }
                        )
                        Text(
                            text = quantity.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                        Text(
                            text = "+",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.clickable { quantity++ }
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    PrimaryButton(
                        text = "ADD TO CART",
                        onClick = { /* Lógica para añadir */ },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // Inteligencia del producto
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = "PRODUCT\nINTELLIGENCE",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Our ISO-PRO Whey Isolate is filtered using a proprietary cold-process micro-filtration system that preserves vital protein fractions. Engineered for rapid absorption, it fuels recovery windows with surgical precision. No fillers. No proprietary blends. Just pure athletic fuel.",
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = 24.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeightSelectorCard(
    weight: String,
    servings: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.DarkGray

    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = weight, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = servings, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
    }
}

@Composable
fun MacroItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductDetailScreenPreview() {
    IronCoreTheme {
        ProductDetailScreen()
    }
}