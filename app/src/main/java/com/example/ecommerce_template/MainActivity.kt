package com.example.ecommerce_template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ecommerce_template.ui.navigation.AppNavigation
import com.example.ecommerce_template.ui.theme.IronCoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IronCoreTheme {
                AppNavigation()
            }
        }
    }
}