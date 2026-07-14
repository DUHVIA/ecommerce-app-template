package com.example.ecommerce_template.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ecommerce_template.MainApplication

import com.example.ecommerce_template.ui.cart.CartScreen
import com.example.ecommerce_template.ui.catalog.HomeScreen
import com.example.ecommerce_template.ui.catalog.ProductDetailScreen
import com.example.ecommerce_template.ui.orders.PurchaseHistoryScreen
import com.example.ecommerce_template.ui.components.core.AppBottomBar
import com.example.ecommerce_template.ui.components.core.AppTopBar
import com.example.ecommerce_template.ui.components.core.NavItem
import com.example.ecommerce_template.ui.cart.CheckoutScreen
import com.example.ecommerce_template.ui.profile.ProfileScreen
import com.example.ecommerce_template.ui.auth.LoginScreen
import com.example.ecommerce_template.ui.auth.RegisterScreen

val BottomNavItems = listOf(
    NavItem("HOME", Icons.Outlined.Home, Routes.HOME),
    NavItem("SHOP", Icons.Outlined.ShoppingCart, Routes.CART),
    NavItem("PROFILE", Icons.Outlined.AccountBox, Routes.PROFILE),
    NavItem("HISTORY", Icons.AutoMirrored.Outlined.List, Routes.HISTORY)
)

@Composable
fun AppNavigation(pendingRoute: String? = null) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Routes.HOME

    val validDeepLinkRoutes = setOf(
        Routes.HOME,
        Routes.CART,
        Routes.HISTORY,
        Routes.PROFILE,
        Routes.LOGIN,
        Routes.REGISTER,
        Routes.CHECKOUT
    )

    LaunchedEffect(pendingRoute) {
        pendingRoute?.let { route ->
            val isStaticRouteValid = route in validDeepLinkRoutes

            val isDynamicProductRoute = route.startsWith("${Routes.PRODUCT_DETAIL}/")

            if (isStaticRouteValid || isDynamicProductRoute) {
                navController.navigate(route) {
                    popUpTo(Routes.HOME) { inclusive = false }
                    launchSingleTop = true
                }
            }
        }
    }

    val showBottomBar = currentRoute in listOf(Routes.HOME, Routes.CART, Routes.HISTORY, Routes.PROFILE)
    val showTopBar = currentRoute in listOf(Routes.HOME, Routes.CART, Routes.HISTORY, Routes.PROFILE)

    val navigateTo: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(Routes.HOME) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    val context = LocalContext.current
    val app = context.applicationContext as MainApplication
    val container = app.container

    val authViewModel = rememberAuthViewModel(container)
    val productViewModel = rememberProductViewModel(container)
    val cartViewModel = rememberCartViewModel(container)
    val checkoutViewModel = rememberCheckoutViewModel(container)
    val orderHistoryViewModel = rememberOrderHistoryViewModel(container)

    Scaffold(
        topBar = {
            if (showTopBar) {
                AppTopBar(
                    onMenuClick = { },
                    onCartClick = { navigateTo(Routes.CART) }
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                AppBottomBar(
                    currentRoute = currentRoute,
                    items = BottomNavItems,
                    onItemClick = { targetRoute -> navigateTo(targetRoute) }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.LOGIN) {
                LoginScreen(
                    authViewModel = authViewModel,
                    onLoginSuccess = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    },
                    onRegisterClick = { navController.navigate(Routes.REGISTER) }
                )
            }

            composable(Routes.REGISTER) {
                RegisterScreen(
                    authViewModel = authViewModel,
                    onRegisterSuccess = { navigateTo(Routes.HOME) },
                    onLoginClick = { navController.popBackStack() }
                )
            }

            composable(Routes.HOME) {
                HomeScreen(
                    productViewModel = productViewModel,
                    onNavigateToDetail = { productId ->
                        navController.navigate("${Routes.PRODUCT_DETAIL}/$productId")
                    }
                )
            }

            composable(Routes.CART) {
                CartScreen(
                    cartViewModel = cartViewModel,
                    onNavigateToDetail = { productId ->
                        navController.navigate("${Routes.PRODUCT_DETAIL}/$productId")
                    },
                    onCheckoutClick = { navigateTo(Routes.CHECKOUT) },
                    onBrowseClick = { navigateTo(Routes.HOME) }
                )
            }

            composable(Routes.CHECKOUT) {
                CheckoutScreen(
                    checkoutViewModel = checkoutViewModel,
                    onOrderPlaced = {
                        navController.navigate(Routes.HISTORY) {
                            popUpTo(Routes.HOME)
                        }
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(Routes.HISTORY) {
                PurchaseHistoryScreen(
                    orderHistoryViewModel = orderHistoryViewModel,
                    onBrowseClick = { navigateTo(Routes.HOME) }
                )
            }

            composable(
                route = "${Routes.PRODUCT_DETAIL}/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId").orEmpty()
                ProductDetailScreen(
                    productId = productId,
                    productViewModel = productViewModel,
                    onBackClick = { navController.popBackStack() },
                    onAddToCart = { id ->
                        productViewModel.addToCart(id)
                    }
                )
            }

            composable(Routes.PROFILE) {
                ProfileScreen(
                    authViewModel = authViewModel,
                    onLogout = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(0)
                        }
                    },
                    onLoginClick = { navController.navigate(Routes.LOGIN) }
                )
            }
        }
    }
}
