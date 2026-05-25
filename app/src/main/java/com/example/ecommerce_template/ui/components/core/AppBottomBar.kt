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
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Immutable
data class CustomBottomBarColors(
    val containerColor: Color,
    val selectedIconColor: Color,
    val unselectedIconColor: Color,
    val selectedTextColor: Color,
    val unselectedTextColor: Color,
    val indicatorColor: Color
)
val defaultBottomNavItems = listOf(
    BottomNavItem("Inicio", Icons.Default.Home, "Route_1"),
    BottomNavItem("Detalle", Icons.Default.ShoppingCart, "Route_2"),
    BottomNavItem("Carrito", Icons.Default.Person, "Route_3"),
    BottomNavItem("Histoial", Icons.Default.CalendarMonth, "Route_4")
)

@Composable
fun getBottomNavColors(): CustomBottomBarColors {
    return CustomBottomBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
        selectedIconColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        selectedTextColor = MaterialTheme.colorScheme.primary,
        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        indicatorColor = MaterialTheme.colorScheme.secondaryContainer
    )
}
@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavItem> = defaultBottomNavItems,
    currentRoute: String = Routes.SCREEN_A,
    colors: CustomBottomBarColors = getBottomNavColors(),
    onItemClick: (String) -> Unit = {}
) {
    NavigationBar(
        modifier = modifier,
        containerColor = colors.containerColor
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isSelected) colors.selectedTextColor else colors.unselectedTextColor,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colors.selectedIconColor,
                    unselectedIconColor = colors.unselectedIconColor,
                    selectedTextColor = Color.Unspecified,
                    unselectedTextColor = Color.Unspecified,
                    indicatorColor = colors.indicatorColor
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