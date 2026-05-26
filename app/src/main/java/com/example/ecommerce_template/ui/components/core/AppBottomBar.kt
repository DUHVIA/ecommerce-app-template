package com.example.ecommerce_template.ui.components.core

import androidx.compose.foundation.layout.RowScope
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
    IronNavItem("SHOP", Icons.Outlined.ShoppingCart, "shop"),
    IronNavItem("PROFILE", Icons.Outlined.Person, "profile")
)

@Composable
fun RowScope.IronBottomBarItem(
    item: IronNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = isSelected,
        onClick = onClick,
        icon = { Icon(item.icon, contentDescription = item.label) },
        label = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicatorColor = Color.Transparent
        )
    )
}

@Composable
fun IronBottomBar(
    items: List<IronNavItem>,
    currentRoute: String,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEach { item ->
            IronBottomBarItem(
                item = item,
                isSelected = currentRoute == item.route,
                onClick = { onItemClick(item.route) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IronBottomBarPreview() {
    MaterialTheme {
        IronBottomBar(
            items = ironBottomNavItems,
            currentRoute = "home",
            onItemClick = {}
        )
    }
}