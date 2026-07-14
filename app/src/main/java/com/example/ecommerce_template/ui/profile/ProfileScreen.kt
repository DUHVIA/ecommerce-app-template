package com.example.ecommerce_template.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import com.example.ecommerce_template.ui.auth.AuthViewModel

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel
) {
    val profileUiState by authViewModel.profileState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Avatar",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = profileUiState.name,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = profileUiState.email,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.weight(1f))

        if (profileUiState.isLoading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (profileUiState.isLoggedIn) {
            PrimaryButton(
                text = if (profileUiState.isLoading) "LOGGING OUT..." else "LOG OUT",
                onClick = { authViewModel.logout(onLogoutSuccess = onLogout) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !profileUiState.isLoading
            )
        } else {
            PrimaryButton(
                text = "INICIAR SESIÓN",
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !profileUiState.isLoading
            )
        }
    }
}
