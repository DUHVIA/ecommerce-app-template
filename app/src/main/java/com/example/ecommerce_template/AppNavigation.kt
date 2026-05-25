package com.example.ecommerce_template

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce_template.screens.CartScreen
import com.example.ecommerce_template.screens.HomeScreen
import com.example.ecommerce_template.screens.ProductDetailScreen
import com.example.ecommerce_template.screens.PurchaseHistoryScreen
import com.example.ecommerce_template.ui.components.core.AppBottomBar
import com.example.ecommerce_template.ui.components.core.AppTopBar
import com.example.ecommerce_template.ui.components.core.BottomNavItem
import com.example.ecommerce_template.ui.components.core.CustomBottomBarColors


val BottomNavItems = listOf(
    BottomNavItem("Inicio", Icons.Default.Home, Routes.SCREEN_A),
    BottomNavItem("Detalle", Icons.Default.ShoppingBag, Routes.SCREEN_C),
    BottomNavItem("Carrito", Icons.Default.ShoppingCart, Routes.SCREEN_B),
    BottomNavItem("Histoial", Icons.Default.AppRegistration, Routes.SCREEN_D)
)

@Composable
fun getBottomNavColorsDark(): CustomBottomBarColors {
    return CustomBottomBarColors(
        containerColor = Color(0xFF1A1A1A),         // Gris oscuro de fondo (puedes ajustarlo)
        selectedIconColor = Color(0xFFCCFF00),      // Verde Lima / Neón activo
        unselectedIconColor = Color(0xFFC4C4C4),    // Gris claro inactivo
        selectedTextColor = Color(0xFFCCFF00),
        unselectedTextColor = Color(0xFFC4C4C4),
        indicatorColor = Color.Transparent
    )
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Routes.SCREEN_A

    Scaffold(
        topBar = { AppTopBar() },
        bottomBar = {
            AppBottomBar(
                currentRoute = currentRoute,
                onItemClick = { targetRoute ->
                    if (currentRoute != targetRoute) {
                        navController.navigate(targetRoute) {
                            popUpTo(Routes.SCREEN_A) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                items = BottomNavItems,
                colors = getBottomNavColorsDark()
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.SCREEN_A,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.SCREEN_A) {
                HomeScreen()
            }

            composable(Routes.SCREEN_B) {
                ProductDetailScreen()
            }

            composable(Routes.SCREEN_C) {
                CartScreen()
            }

            composable(Routes.SCREEN_D) {
                PurchaseHistoryScreen()
            }
        }
    }
}