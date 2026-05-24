package com.example.ecommerce_template

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
                }
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