package com.example.ecommerce_template.ui.components.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecommerce_template.ui.theme.IronCoreTheme

//SOLID APPLIED

// 1. Componente de Título (Responsabilidad: Solo renderizar textos de cabecera)
@Composable
fun TopBarTitle(title: String = "IRON CORE") {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium
    )
}

// 2. Componente de Navegación (Responsabilidad: Solo renderizar el ícono izquierdo)
@Composable
fun TopBarNavigationIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menú",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

// 3. Componente de Acción (Responsabilidad: Solo renderizar los botones derechos, como el carrito)
@Composable
fun TopBarCartAction(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick, modifier = modifier) {
        // Aquí luego podemos envolver el ícono en un BadgeBox
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            contentDescription = "Carrito",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

// 4. Contenedor Principal (Responsabilidad: Orquestar el layout general)
// Cumple con OCP: Está abierto a extensión (puedes pasarle otros composables) pero cerrado a modificación.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    title: String = "IRON CORE"
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { TopBarTitle(title) },
        navigationIcon = { TopBarNavigationIcon(onClick = onMenuClick) },
        actions = { TopBarCartAction(onClick = onCartClick) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AppTopBarPreview() {
    IronCoreTheme {
        AppTopBar()
    }
}