package com.example.ecommerce_template.ui.components.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

data class IronNavItem(val label: String, val icon: ImageVector, val route: String)

val ironBottomNavItems = listOf(
    IronNavItem("HOME", Icons.Outlined.Home, "home"),
    IronNavItem("SHOP", Icons.Outlined.ShoppingCart, "shop"), // Shop en vez de Carrito según tu diseño
    IronNavItem("PROFILE", Icons.Outlined.Person, "profile")
)

@Composable
fun IronBottomBar(
    modifier: Modifier = Modifier,
    items: List<IronNavItem> = ironBottomNavItems,
    currentRoute: String = "home",
    onItemClick: (String) -> Unit = {}
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background, // Negro profundo
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary, // Ícono Verde
                    selectedTextColor = MaterialTheme.colorScheme.primary, // Texto Verde
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant, // Gris
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = Color.Transparent // Quitamos el círculo de fondo por defecto
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBottomBarPreview() {
    MaterialTheme {
        IronBottomBar()
    }
}