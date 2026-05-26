package com.example.ecommerce_template.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerce_template.ui.components.core.IronPasswordField
import com.example.ecommerce_template.ui.components.core.IronTextFieldBase
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import com.example.ecommerce_template.ui.theme.IronCoreTheme

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado de Marca
        Text(
            text = "WELCOME TO",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "IRON CORE",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Input de Email (Reutilizando la base SOLID)
        IronTextFieldBase(
            value = email,
            onValueChange = { email = it },
            placeholder = {
                Text("EMAIL ADDRESS", style = MaterialTheme.typography.labelLarge, color = Color.Gray)
            },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = "Email", tint = Color.Gray)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input de Contraseña (Componente especializado)
        IronPasswordField(
            value = password,
            onValueChange = { password = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Olvidé mi contraseña
        Text(
            text = "FORGOT PASSWORD?",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { /* Lógica recuperar */ }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de Acción Principal
        PrimaryButton(
            text = "START TRAINING", // Texto temático de gimnasio en lugar de "Login"
            onClick = onLoginSuccess
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Crear Cuenta
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "NEW TO IRON CORE? ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = "CREATE AN ACCOUNT",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onRegisterClick() }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    IronCoreTheme {
        LoginScreen(onLoginSuccess = {}, onRegisterClick = {})
    }
}