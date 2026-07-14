package com.example.ecommerce_template.di

import android.content.Context
import com.example.ecommerce_template.data.address.AddressRepository
import com.example.ecommerce_template.data.auth.TokenStore
import com.example.ecommerce_template.data.auth.UserRepository
import com.example.ecommerce_template.data.cart.CartRepository
import com.example.ecommerce_template.local.AppDatabase
import com.example.ecommerce_template.network.AuthInterceptor
import com.example.ecommerce_template.network.NetworkMonitor
import com.example.ecommerce_template.network.RetrofitFactory
import com.example.ecommerce_template.network.TokenAuthenticator
import com.example.ecommerce_template.data.order.OrderRepository
import com.example.ecommerce_template.data.product.ProductRepository
import com.example.ecommerce_template.core.SearchLogger
import com.example.ecommerce_template.core.SessionManager

class AppContainer(applicationContext: Context) {

    val networkMonitor: NetworkMonitor = NetworkMonitor(applicationContext)
    private val database: AppDatabase = AppDatabase.build(applicationContext)

    private val tokenStore: TokenStore = TokenStore(applicationContext)
    private val authInterceptor: AuthInterceptor = AuthInterceptor(tokenStore)

    private val retrofitFactoryHolder: Lazy<RetrofitFactory> = lazy {
        val authenticator = TokenAuthenticator(tokenStore) { retrofitFactoryHolder.value.authApi }
        RetrofitFactory(authInterceptor, authenticator)
    }

    private val retrofit: RetrofitFactory
        get() = retrofitFactoryHolder.value

    val sessionManager: SessionManager = SessionManager(tokenStore)
    val searchLogger: SearchLogger = SearchLogger()

    val userRepository: UserRepository = UserRepository(
        authApi = retrofit.authApi,
        userApi = retrofit.userApi,
        tokenStore = tokenStore
    )

    val productRepository: ProductRepository = ProductRepository(
        catalogApi = retrofit.catalogApi,
        productDao = database.productDao(),
        networkMonitor = networkMonitor,
        logger = searchLogger
    )

    val cartRepository: CartRepository = CartRepository(
        cartApi = retrofit.cartApi,
        networkMonitor = networkMonitor,
        logger = searchLogger
    )

    val addressRepository: AddressRepository = AddressRepository(
        addressApi = retrofit.addressApi
    )

    val orderRepository: OrderRepository = OrderRepository(
        orderApi = retrofit.orderApi
    )
}
