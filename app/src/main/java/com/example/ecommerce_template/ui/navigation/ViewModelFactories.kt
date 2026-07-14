package com.example.ecommerce_template.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.WorkManager
import com.example.ecommerce_template.MainApplication
import com.example.ecommerce_template.di.AppContainer
import com.example.ecommerce_template.ui.auth.AuthViewModel
import com.example.ecommerce_template.ui.cart.CartViewModel
import com.example.ecommerce_template.ui.cart.CheckoutViewModel
import com.example.ecommerce_template.ui.catalog.ProductViewModel
import com.example.ecommerce_template.ui.orders.OrderHistoryViewModel

@Composable
fun rememberAuthViewModel(container: AppContainer): AuthViewModel = viewModel(
    factory = viewModelFactory {
        initializer { AuthViewModel(userRepository = container.userRepository) }
    }
)

@Composable
fun rememberProductViewModel(container: AppContainer): ProductViewModel {
    val context = LocalContext.current
    return viewModel(
        factory = viewModelFactory {
            initializer {
                ProductViewModel(
                    productRepository = container.productRepository,
                    cartRepository = container.cartRepository,
                    workManager = WorkManager.getInstance(context)
                )
            }
        }
    )
}

@Composable
fun rememberCartViewModel(container: AppContainer): CartViewModel = viewModel(
    factory = viewModelFactory {
        initializer { CartViewModel(cartRepository = container.cartRepository) }
    }
)

@Composable
fun rememberCheckoutViewModel(container: AppContainer): CheckoutViewModel = viewModel(
    factory = viewModelFactory {
        initializer {
            CheckoutViewModel(
                cartRepository = container.cartRepository,
                orderRepository = container.orderRepository,
                addressRepository = container.addressRepository,
                networkMonitor = container.networkMonitor
            )
        }
    }
)

@Composable
fun rememberOrderHistoryViewModel(container: AppContainer): OrderHistoryViewModel = viewModel(
    factory = viewModelFactory {
        initializer { OrderHistoryViewModel(orderRepository = container.orderRepository) }
    }
)
