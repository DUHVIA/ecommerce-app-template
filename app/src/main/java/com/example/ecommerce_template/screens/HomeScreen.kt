package com.example.ecommerce_template.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerce_template.ui.components.core.SearchBar



@Composable
fun HomeScreen() {
    val customSearchColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        cursorColor = Color(0xFF6B7280)
    )

    val customSearchTextStyle = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 2.sp,
        color = Color(0xFF6B7280)
    )

    val customSearchShape = RoundedCornerShape(8.dp)

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF0F0F0F)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Home",
            textAlign = TextAlign.Center
        )

        var searchText = ""
        SearchBar(
            value = searchText,
            onValueChange = { searchText = it },
            placeholderText = "SEARCH PERFORMANCE GEAR...",
            shape = customSearchShape,
            colors = customSearchColors,
            textStyle = customSearchTextStyle,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}