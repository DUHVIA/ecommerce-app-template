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
import com.example.ecommerce_template.ui.theme.IronCoreTheme

data class NavItem(val label: String, val icon: ImageVector, val route: String)

val ironBottomNavItems = listOf(
    NavItem("HOME", Icons.Outlined.Home, "home"),
    NavItem("SHOP", Icons.Outlined.ShoppingCart, "shop"),
    NavItem("PROFILE", Icons.Outlined.Person, "profile")
)

//SOLID APPLIED
@Composable
fun RowScope.AppBottomBarItem(
    item: NavItem,
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
fun AppBottomBar(
    items: List<NavItem>,
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
            AppBottomBarItem(
                item = item,
                isSelected = currentRoute == item.route,
                onClick = { onItemClick(item.route) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBottomBarPreview() {
    IronCoreTheme {
        AppBottomBar(
            items = ironBottomNavItems,
            currentRoute = "home",
            onItemClick = {}
        )
    }
}