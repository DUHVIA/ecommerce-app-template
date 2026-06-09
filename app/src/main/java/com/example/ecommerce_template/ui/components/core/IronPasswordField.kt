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
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecommerce_template.ui.theme.IronCoreTheme


//SOLID APPLIED
// 1. Componente del Ícono Principal (SRP: Solo dibuja el candado)
@Composable
fun IronPasswordLeadingIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Default.Lock,
        contentDescription = "Contraseña",
        tint = Color.Gray,
        modifier = modifier
    )
}

// 2. Componente del Placeholder (SRP: Solo maneja la tipografía del texto de fondo)
@Composable
fun IronPasswordPlaceholder(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = Color.Gray,
        modifier = modifier
    )
}

// 3. Componente del Toggle de Visibilidad (SRP: Solo se encarga de mostrar el botón del ojo y emitir el evento de clic)
@Composable
fun IronPasswordVisibilityToggle(
    isVisible: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val image = if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
    val description = if (isVisible) "Ocultar contraseña" else "Mostrar contraseña"

    IconButton(onClick = onToggle, modifier = modifier) {
        Icon(imageVector = image, contentDescription = description, tint = Color.Gray)
    }
}

// 4. El Orquestador Principal (SRP: Conecta el estado de visibilidad con los componentes visuales inyectados)
@Composable
fun IronPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "PASSWORD"
) {
    // El estado se mantiene aquí, pero la representación visual se delega
    var passwordVisible by remember { mutableStateOf(false) }

    IronTextFieldBase(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = {
            IronPasswordPlaceholder(text = placeholderText)
        },
        leadingIcon = {
            IronPasswordLeadingIcon()
        },
        trailingIcon = {
            IronPasswordVisibilityToggle(
                isVisible = passwordVisible,
                onToggle = { passwordVisible = !passwordVisible }
            )
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

// 5. Preview Integrado
@Preview(showBackground = true)
@Composable
fun IronPasswordFieldPreview() {
    IronCoreTheme {
        IronPasswordField(
            value = "secreto123",
            onValueChange = {}
        )
    }
}