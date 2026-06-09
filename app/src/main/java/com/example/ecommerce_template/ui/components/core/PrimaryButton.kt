package com.example.ecommerce_template.ui.components.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerce_template.ui.theme.IronCoreTheme

//SOLID APPLIED
// 1. Componente de Contenido Interno (SRP: Solo se encarga de acomodar el texto y un ícono)
@Composable
fun IronButtonContent(
    text: String,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    if (leadingIcon != null) {
        leadingIcon()
        Spacer(modifier = Modifier.width(8.dp))
    }
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelLarge
    )
}

// 2. Contenedor Primario Base (OCP: Abierto a cualquier contenido, cerrado a modificación de su estilo base)
@Composable
fun PrimaryButtonBase(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        enabled = enabled,
        content = content
    )
}

// 3. Sobrecarga por Conveniencia (Lo que realmente usarás en el 90% de los casos)
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    PrimaryButtonBase(onClick = onClick, modifier = modifier, enabled = enabled) {
        IronButtonContent(text = text, leadingIcon = leadingIcon)
    }
}

// 4. Contenedor Secundario Base (OCP)
@Composable
fun SecondaryOutlinedButtonBase(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        enabled = enabled,
        content = content
    )
}

// 5. Sobrecarga por Conveniencia Secundario
@Composable
fun SecondaryOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    SecondaryOutlinedButtonBase(onClick = onClick, modifier = modifier, enabled = enabled) {
        IronButtonContent(text = text, leadingIcon = leadingIcon)
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonsPreview() {
    IronCoreTheme {
        Column {
            PrimaryButton(text = "Comprar Proteína", onClick = {})
            Spacer(modifier = Modifier.height(8.dp))
            SecondaryOutlinedButton(text = "Ver Detalles", onClick = {})
        }
    }
}