package com.example.ecommerce_template.di

import com.example.ecommerce_template.data.auth.UserRepository
import com.example.ecommerce_template.data.network.AuthInterceptor
import com.example.ecommerce_template.data.network.IronCoreApiService
import com.example.ecommerce_template.data.product.ProductRepository
import com.example.ecommerce_template.data.utils.SearchLogger
import com.example.ecommerce_template.data.utils.SessionManager
import com.example.ecommerce_template.data.utils.TokenManager
import com.example.ecommerce_template.ui.viewModel.AuthViewModel
import com.example.ecommerce_template.ui.viewModel.ProductViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val appModule = module {
    // Utilidades
    single { SessionManager() }
    single { SearchLogger(get()) }
    single { TokenManager(androidContext()) }

    // Red (Network)
    single { AuthInterceptor(get()) }
    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    single {
        val json = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .baseUrl("https://backend-ecomerce-danp-n997dm6iq-manix1.vercel.app/")
            .client(get())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    single { get<Retrofit>().create(IronCoreApiService::class.java) }

    // Repositorios
    single { ProductRepository(get(), get()) }
    single { UserRepository(get(), get()) }

    // ViewModels
    viewModel { ProductViewModel(get()) }
    viewModel { AuthViewModel(get()) }
}