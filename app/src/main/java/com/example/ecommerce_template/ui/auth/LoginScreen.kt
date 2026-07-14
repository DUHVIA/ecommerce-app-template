package com.example.ecommerce_template.ui.auth

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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerce_template.ui.components.core.PasswordField
import com.example.ecommerce_template.ui.components.core.TextFieldBase
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import com.example.ecommerce_template.ui.auth.AuthViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    authViewModel: AuthViewModel
) {
    val loginUiState by authViewModel.loginState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ENCABEZADO
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

        // 2. INPUTS CONECTADOS AL UiSTATE
        TextFieldBase(
            value = loginUiState.email, // Usa el estado recolectado
            onValueChange = { authViewModel.onEmailChange(it) },
            placeholder = {
                Text("EMAIL ADDRESS", style = MaterialTheme.typography.labelLarge, color = Color.Gray)
            },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = "Email", tint = Color.Gray)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            value = loginUiState.password, // Usa el estado recolectado
            onValueChange = { authViewModel.onPasswordChange(it) },
        )

        Spacer(modifier = Modifier.height(32.dp))

        // MOSTRAR ERROR DESDE EL UiSTATE
        if (loginUiState.loginError != null) {
            Text(
                text = loginUiState.loginError!!,
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

        // 3. BOTÓN DE LOGIN CON MANEJO DE LOADING
        PrimaryButton(
            text = if (loginUiState.isLoading) "LOADING..." else "START TRAINING",
            onClick = { authViewModel.login(onSuccess = onLoginSuccess) },
            enabled = !loginUiState.isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 4. BOTÓN DE DIRIGIR A CREAR CUENTA
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
                modifier = Modifier.clickable(enabled = !loginUiState.isLoading) { onRegisterClick() }
            )
        }
    }
}