package com.example.ecommerce_template.ui.components.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerce_template.ui.theme.IronCoreTheme

//SOLID APPLIED
// 1. Componente de Etiqueta (SRP: Solo dibuja el rectangulito de color con texto)
@Composable
fun IronBadge(
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall.copy(color = Color.Black),
        modifier = modifier
            .background(color, RoundedCornerShape(2.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    )
}

// 2. Componente de Imagen (SRP: Maneja el contenedor cuadrado y acepta una etiqueta superpuesta vía Slot - OCP)
@Composable
fun IronProductImage(
    modifier: Modifier = Modifier,
    badgeSlot: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Cuadrado perfecto
            .background(Color(0xFF0F0F0F), RoundedCornerShape(4.dp))
    ) {
        // Aquí irá AsyncImage(model = url...) cuando conectemos la API

        if (badgeSlot != null) {
            Box(modifier = Modifier.padding(8.dp)) {
                badgeSlot()
            }
        }
    }
}

// 3. Componente de Textos (SRP: Maneja la jerarquía tipográfica del producto)
@Composable
fun IronProductInfo(
    category: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = category.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// 4. Componente de Acción Rápida (SRP: Solo el botón circular de añadir)
@Composable
fun IronAddToCartButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(36.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color(0xFF333333),
            contentColor = Color.White
        )
    ) {
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            contentDescription = "Añadir",
            modifier = Modifier.size(18.dp)
        )
    }
}

// 5. El Orquestador Principal (OCP: Usa los componentes anteriores para ensamblar la tarjeta)
@Composable
fun IronProductCard(
    category: String,
    title: String,
    price: Double,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier,
    badgeText: String? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // Slot 1: Imagen + Etiqueta opcional
            IronProductImage(
                badgeSlot = if (badgeText != null) {
                    { IronBadge(text = badgeText) }
                } else null
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Slot 2: Información del producto
            IronProductInfo(category = category, title = title)

            Spacer(modifier = Modifier.height(8.dp))

            // Slot 3: Precio y Botón alineados
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$$price",
                    style = MaterialTheme.typography.labelLarge
                )
                IronAddToCartButton(onClick = onAddToCart)
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun IronProductCardPreview() {
    IronCoreTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            IronProductCard(
                badgeText = "NEW",
                category = "Calzado",
                title = "Zapatillas Iron Runner Pro",
                price = 129.99,
                onAddToCart = {},
                modifier = Modifier.width(180.dp)
            )
        }
    }
}