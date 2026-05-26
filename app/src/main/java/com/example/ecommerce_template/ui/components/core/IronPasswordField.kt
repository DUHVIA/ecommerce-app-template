package com.example.ecommerce_template.ui.components.core

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun IronPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "PASSWORD"
) {
    var passwordVisible by remember { mutableStateOf(false) }

    IronTextFieldBase(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Gray
            )
        },
        leadingIcon = {
            Icon(Icons.Default.Lock, contentDescription = "Contraseña", tint = Color.Gray)
        },
        trailingIcon = {
            val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
            val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = description, tint = Color.Gray)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}