package com.example.ecommerce_template.ui.components.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerce_template.ui.theme.IronCoreTheme

//SOLID APPLIED
// 1. Componente del Ícono (SRP: Solo define cómo se ve el ícono en los chips)
@Composable
fun CategoryChipIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
) {
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        tint = Color.Gray,
        modifier = modifier.size(28.dp)
    )
}

// 2. Componente del Texto (SRP: Solo maneja la tipografía y color del título del chip)
@Composable
fun CategoryChipText(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelSmall,
        color = Color.Gray,
        modifier = modifier
    )
}

// 3. Contenedor Base (OCP: Abierto a recibir cualquier diseño interno mediante el 'content')
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChipBase(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .width(100.dp)
            .height(100.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(), // Reemplazamos el size(100.dp) redundante para que ocupe el Card
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            content = content
        )
    }
}

// 4. El Componente Final (Orquestador: Une la base con el ícono y el texto)
@Composable
fun CategoryChip(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CategoryChipBase(
        onClick = onClick,
        modifier = modifier
    ) {
        CategoryChipIcon(icon = icon, contentDescription = title)
        Spacer(modifier = Modifier.height(12.dp))
        CategoryChipText(title = title)
    }
}

// 5. Preview Integrado
@Preview(name = "Multiple Chips Row", showBackground = true)
@Composable
fun CategoryChipsListPreview() {
    IronCoreTheme { // Cambia esto a IronCoreTheme si lo estás usando
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background // Para ver bien el contraste oscuro
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CategoryChip(
                    title = "Inicio",
                    icon = Icons.Default.Home,
                    onClick = {}
                )
                CategoryChip(
                    title = "Carrito",
                    icon = Icons.Default.ShoppingCart,
                    onClick = {}
                )
                CategoryChip(
                    title = "Favoritos",
                    icon = Icons.Default.Favorite,
                    onClick = {}
                )
            }
        }
    }
}