package com.example.ecommerce_template.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce_template.ui.screens.CartScreen
import com.example.ecommerce_template.ui.screens.HomeScreen
import com.example.ecommerce_template.ui.screens.ProductDetailScreen
import com.example.ecommerce_template.ui.screens.PurchaseHistoryScreen
import com.example.ecommerce_template.ui.components.core.IronBottomBar
import com.example.ecommerce_template.ui.components.core.IronCoreTopBar
import com.example.ecommerce_template.ui.components.core.IronNavItem

// Definimos solo 3 items principales para la barra inferior
val BottomNavItems = listOf(
    IronNavItem("HOME", Icons.Outlined.Home, Routes.HOME),
    IronNavItem("SHOP", Icons.Outlined.ShoppingCart, Routes.CART),
    IronNavItem("HISTORY", Icons.Outlined.List, Routes.HISTORY)
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Routes.HOME

    // Ocultamos el BottomBar si estamos en el detalle del producto
    val showBottomBar = currentRoute in listOf(Routes.HOME, Routes.CART, Routes.HISTORY)

    Scaffold(
        topBar = {
            // La barra superior se mantiene global en este caso
            IronCoreTopBar(
                onMenuClick = { /* Abrir drawer en el futuro */ },
                onCartClick = { navController.navigate(Routes.CART) }
            )
        },
        bottomBar = {
            if (showBottomBar) {
                IronBottomBar(
                    currentRoute = currentRoute,
                    items = BottomNavItems,
                    onItemClick = { targetRoute ->
                        if (currentRoute != targetRoute) {
                            navController.navigate(targetRoute) {
                                // Evita acumular múltiples copias del mismo destino
                                popUpTo(Routes.HOME) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                // Si HomeScreen necesita navegar al detalle, puedes pasarle un lambda:
                // HomeScreen(onNavigateToDetail = { navController.navigate(Routes.PRODUCT_DETAIL) })
                HomeScreen()
            }

            composable(Routes.CART) {
                CartScreen(onNavigateToDetail = { productId ->
                    // 🧭 Viaja a la pantalla de detalle
                    navController.navigate(Routes.PRODUCT_DETAIL)
                })
            }

            composable(Routes.HISTORY) {
                PurchaseHistoryScreen()
            }

            composable(Routes.PRODUCT_DETAIL) {
                ProductDetailScreen()
            }
        }
    }
}