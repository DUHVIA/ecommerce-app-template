package com.example.ecommerce_template.ui.screens.auth

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.ecommerce_template.ui.viewModel.AuthViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,

    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    // 1. RECOLECTAR EL ESTADO DE REGISTRO DESDE EL VIEWMODEL
    val registerUiState by viewModel.registerState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado temático
        Text(
            text = "JOIN THE",
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

        // Campo: Nombre Completo
        IronTextFieldBase(
            value = registerUiState.name,
            onValueChange = { viewModel.onRegisterNameChange(it) },
            placeholder = {
                Text("FULL NAME", style = MaterialTheme.typography.labelLarge, color = Color.Gray)
            },
            leadingIcon = {
                androidx.compose.material3.Icon(Icons.Default.Person, contentDescription = "User", tint = Color.Gray)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo: Email
        IronTextFieldBase(
            value = registerUiState.email,
            onValueChange = { viewModel.onRegisterEmailChange(it) },
            placeholder = {
                Text("EMAIL ADDRESS", style = MaterialTheme.typography.labelLarge, color = Color.Gray)
            },
            leadingIcon = {
                androidx.compose.material3.Icon(Icons.Default.Email, contentDescription = "Email", tint = Color.Gray)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo: Password
        IronPasswordField(
            value = registerUiState.password,
            onValueChange = { viewModel.onRegisterPasswordChange(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo: Confirm Password
        IronPasswordField(
            value = registerUiState.confirmPassword,
            onValueChange = { viewModel.onRegisterConfirmPasswordChange(it) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (registerUiState.errorMessage != null) {
            Text(
                text = registerUiState.errorMessage!!,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        PrimaryButton(
            text = "JOIN NOW",
            onClick = {
                viewModel.register(onRegisterSuccess)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Volver al Login
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "ALREADY AN ATHLETE? ",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = "LOG IN",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onLoginClick() }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    IronCoreTheme {
        RegisterScreen(onRegisterSuccess = {}, onLoginClick = {})
    }
}