package com.example.ecommerce_template

import android.R.attr.strokeWidth
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        containerColor = Color(0xFF1A1A1A),
        selectedIconColor = Color(0xFFCCFF00),
        unselectedIconColor = Color(0xFFC4C4C4),
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
                colors = getBottomNavColorsDark(),
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        val lineThickness = 6.dp.toPx()
                        val y = 0f

                        drawLine(
                            color = Color(0xFF2C2C2C),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = lineThickness
                        )
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