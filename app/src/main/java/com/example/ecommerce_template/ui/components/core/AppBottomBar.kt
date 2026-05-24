package com.example.ecommerce_template.ui.components.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecommerce_template.Routes

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val defaultBottomNavItems = listOf(
    BottomNavItem("Inicio", Icons.Default.Home, Routes.SCREEN_A),
    BottomNavItem("Detalle", Icons.Default.ShoppingCart, Routes.SCREEN_C),
    BottomNavItem("Carrito", Icons.Default.Person, Routes.SCREEN_B),
    BottomNavItem("Histoial", Icons.Default.CalendarMonth, Routes.SCREEN_D)
)

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavItem> = defaultBottomNavItems,
    currentRoute: String = Routes.SCREEN_A,
    onItemClick: (String) -> Unit = {}
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(
                    item.route
                ) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBottomBarPreview() {
    MaterialTheme {
        AppBottomBar()
    }
}