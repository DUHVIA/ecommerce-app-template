package com.example.ecommerce_template.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ecommerce_template.ui.screens.CartScreen
import com.example.ecommerce_template.ui.screens.HomeScreen
import com.example.ecommerce_template.ui.screens.ProductDetailScreen
import com.example.ecommerce_template.ui.screens.PurchaseHistoryScreen
import com.example.ecommerce_template.ui.components.core.IronBottomBar
import com.example.ecommerce_template.ui.components.core.IronCoreTopBar
import com.example.ecommerce_template.ui.components.core.IronNavItem
import com.example.ecommerce_template.ui.screens.CheckoutScreen
import com.example.ecommerce_template.ui.screens.ProfileScreen
import com.example.ecommerce_template.ui.screens.auth.LoginScreen
import com.example.ecommerce_template.ui.screens.auth.RegisterScreen

val BottomNavItems = listOf(
    IronNavItem("HOME", Icons.Outlined.Home, Routes.HOME),
    IronNavItem("SHOP", Icons.Outlined.ShoppingCart, Routes.CART),
    IronNavItem("PROFILE", Icons.Outlined.AccountBox, Routes.PROFILE),
    IronNavItem("HISTORY", Icons.AutoMirrored.Outlined.List, Routes.HISTORY),
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route ?: Routes.HOME

    val showBottomBar = currentRoute in listOf(Routes.HOME, Routes.CART, Routes.HISTORY,Routes.PROFILE)
    val showTopBar = currentRoute in listOf(Routes.HOME, Routes.CART, Routes.HISTORY,Routes.PROFILE)

    val navigateTo: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(Routes.HOME) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        topBar = {
            if (showTopBar){
                IronCoreTopBar(
                    onMenuClick = { /* Abrir drawer en el futuro */ },
                    onCartClick = { navigateTo(Routes.CART) }
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                IronBottomBar(
                    currentRoute = currentRoute,
                    items = BottomNavItems,
                    onItemClick = { targetRoute -> navigateTo(targetRoute) }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.LOGIN,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.LOGIN) {
                LoginScreen(
                    onLoginSuccess = {
                        navigateTo(Routes.HOME)
                    },
                    onRegisterClick = {
                        navigateTo(Routes.REGISTER)
                    }
                )
            }

            composable (Routes.REGISTER) {
                RegisterScreen(
                    onRegisterSuccess = {
                        navigateTo(Routes.HOME)
                    },
                    onLoginClick = {
                        navigateTo(Routes.LOGIN)
                    }
                )
            }


            composable(Routes.HOME) {
                HomeScreen(
                    onNavigateToDetail = { productId ->
                        navController.navigate("${Routes.PRODUCT_DETAIL}/$productId")
                    }
                )
            }

            composable(Routes.CART) {
                CartScreen(
                    onNavigateToDetail = { productId ->
                        navController.navigate("${Routes.PRODUCT_DETAIL}/$productId")
                    },
                    onCheckoutClick = {
                        navigateTo(Routes.CHECKOUT)
                    }
                )
            }

            composable(Routes.CHECKOUT) {
                CheckoutScreen(
                    onOrderPlaced = {
                        navigateTo(Routes.HISTORY)
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Routes.HISTORY) {
                PurchaseHistoryScreen()
            }

            composable(
                route = "${Routes.PRODUCT_DETAIL}/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId") ?: 0

                ProductDetailScreen(
                    productId = productId,
                    onBackClick = { navController.popBackStack() },
                )
            }

            composable(Routes.PROFILE) {
                ProfileScreen(
                    onBackClick = { navController.popBackStack() },
                    onLogout = { navigateTo(Routes.LOGIN) }
                )
            }
        }
    }
}