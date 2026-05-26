package com.example.ecommerce_template.ui.components.core

import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecommerce_template.ui.theme.IronCoreTheme

@Composable
fun IronTopBarTitle(title: String = "IRON CORE") {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
fun IronTopBarNavigationIcon(
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

@Composable
fun IronTopBarCartAction(
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IronCoreTopBar(
    modifier: Modifier = Modifier,
    // Usamos las funciones que creamos arriba como valores por defecto
    title: @Composable () -> Unit = { IronTopBarTitle() },
    navigationIcon: @Composable () -> Unit = { IronTopBarNavigationIcon(onClick = {}) },
    actions: @Composable RowScope.() -> Unit = { IronTopBarCartAction(onClick = {}) }
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background // Negro profundo
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AppTopBarPreview() {
    IronCoreTheme {
        // Al llamarlo así, usa los componentes por defecto
        IronCoreTopBar()
    }
}