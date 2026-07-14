package com.example.ecommerce_template.ui.components.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import com.example.ecommerce_template.ui.theme.IronCoreTheme

//SOLID APPLIED
// 1. Componente de Título (SRP: Su única razón de cambio es si cambia el estilo del título)
@Composable
fun ProductCardTitle(
    nombre: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = nombre,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold
        ),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}

// 2. Componente de Precio (SRP: Maneja la lógica de la moneda y el estilo del precio)
@Composable
fun ProductCardPrice(
    modifier: Modifier = Modifier,
    precio: Double,
    moneda: String = "S/"
) {
    Text(
        text = "$moneda $precio",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}

// 3. Contenedor Base (OCP: Abierto a extensión mediante "slots" (lambdas), cerrado a modificación)
@Composable
fun ProductCardBase(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ),
    titleSlot: @Composable () -> Unit,
    priceSlot: @Composable () -> Unit,
    actionSlot: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        colors = colors
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            titleSlot()
            Spacer(modifier = Modifier.height(4.dp))

            priceSlot()
            Spacer(modifier = Modifier.height(16.dp))

            actionSlot()
        }
    }
}

// 4. Componente de Dominio Específico (El que realmente usas en tu vista, uniendo las piezas)
@Composable
fun ProductListItem(
    nombre: String,
    precio: Double,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    )
) {
    ProductCardBase(
        modifier = modifier,
        shape = shape,
        colors = colors,
        titleSlot = { ProductCardTitle(nombre = nombre) },
        priceSlot = { ProductCardPrice(precio = precio) },
        actionSlot = {
            // Reutilizamos el componente core para mantener consistencia
            PrimaryButton(
                text = "Agregar",
                onClick = onAddClick,
                modifier = Modifier.wrapContentWidth()
            )
        }
    )
}

// 5. Preview Integrado
@Preview(showBackground = true)
@Composable
fun ProductListItemPreview() {
    // Usamos el IronCoreTheme para que herede los colores oscuros de Duhvia que configuramos antes
    IronCoreTheme {
        ProductListItem(
            nombre = "Proteína Whey Aislada 2kg",
            precio = 249.90,
            onAddClick = {}
        )
    }
}