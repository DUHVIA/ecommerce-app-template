package com.example.ecommerce_template.ui.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecommerce_template.data.address.Address
import com.example.ecommerce_template.ui.components.core.PrimaryButton
import com.example.ecommerce_template.ui.cart.CheckoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    onOrderPlaced: () -> Unit,
    onBackClick: () -> Unit,
    checkoutViewModel: CheckoutViewModel
) {
    val uiState by checkoutViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        checkoutViewModel.refresh()
    }
    LaunchedEffect(uiState.errorMessage) {
        val msg = uiState.errorMessage
        if (!msg.isNullOrBlank()) {
            snackbarHostState.showSnackbar(msg)
            checkoutViewModel.consumeError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "IRON CORE",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "CHECKOUT",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "SHIPPING ADDRESS",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                val selected = uiState.addresses.firstOrNull { it.id == uiState.selectedAddressId }
                if (selected != null) {
                    Text(
                        text = selected.displayLabel,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                } else {
                    Text(
                        text = "Sin dirección seleccionada",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { checkoutViewModel.openAddressSheet() }) {
                    Text(if (selected == null) "AGREGAR DIRECCIÓN" else "CAMBIAR DIRECCIÓN")
                }

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "ORDER SUMMARY",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (uiState.items.isEmpty()) {
                    Text(
                        text = "No hay productos en el carrito.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                } else {
                    uiState.items.forEach { (label, _) ->
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                Spacer(modifier = Modifier.height(16.dp))

                PriceRow("Subtotal", uiState.subtotal)
                PriceRow("Taxes", uiState.taxes)
                PriceRow("Shipping", uiState.shipping)

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "TOTAL",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "S/ ${"%.2f".format(uiState.total)}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))

                PrimaryButton(
                    text = if (uiState.isPlacingOrder) "PROCESANDO..." else "CONFIRM PURCHASE",
                    onClick = { checkoutViewModel.placeOrder(onPlaced = onOrderPlaced) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isPlacingOrder && !uiState.isEmpty && uiState.selectedAddressId != null
                )
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) { data -> Snackbar(snackbarData = data) }
        }
    }

    if (uiState.isAddressSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { checkoutViewModel.closeAddressSheet() },
            sheetState = sheetState
        ) {
            AddressSelector(
                addresses = uiState.addresses,
                selectedId = uiState.selectedAddressId,
                onSelect = {
                    checkoutViewModel.selectAddress(it)
                    checkoutViewModel.closeAddressSheet()
                },
                onCreateNew = { checkoutViewModel.openAddressForm() }
            )
        }
    }

    if (uiState.isAddressFormOpen) {
        AddressFormDialog(
            form = uiState.addressForm,
            onChange = { checkoutViewModel.updateAddressForm(it) },
            onDismiss = { checkoutViewModel.closeAddressForm() },
            onConfirm = { checkoutViewModel.createAddress() }
        )
    }
}

@Composable
private fun AddressSelector(
    addresses: List<Address>,
    selectedId: String?,
    onSelect: (String) -> Unit,
    onCreateNew: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "Selecciona una dirección",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (addresses.isEmpty()) {
            Text(
                text = "Aún no tienes direcciones registradas.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            addresses.forEach { addr ->
                val isSelected = addr.id == selectedId
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                            else Color.Transparent,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { onSelect(addr.id) }
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = addr.displayLabel,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        if (addr.isDefault) {
                            Text(
                                text = "Predeterminada",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(
            text = "AGREGAR NUEVA DIRECCIÓN",
            onClick = onCreateNew,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun AddressFormDialog(
    form: com.example.ecommerce_template.ui.cart.AddressFormState,
    onChange: (com.example.ecommerce_template.ui.cart.AddressFormState) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva dirección") },
        text = {
            Column {
                OutlinedTextField(
                    value = form.street,
                    onValueChange = { onChange(form.copy(street = it)) },
                    label = { Text("Calle y número") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = form.city,
                    onValueChange = { onChange(form.copy(city = it)) },
                    label = { Text("Ciudad") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = form.state,
                    onValueChange = { onChange(form.copy(state = it)) },
                    label = { Text("Estado / Departamento") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = form.zipCode,
                    onValueChange = { onChange(form.copy(zipCode = it)) },
                    label = { Text("Código postal") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = form.isDefault,
                        onCheckedChange = { onChange(form.copy(isDefault = it)) }
                    )
                    Text("Marcar como predeterminada")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("GUARDAR") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("CANCELAR") }
        }
    )
}

@Composable
fun PriceRow(label: String, value: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Text("S/ ${"%.2f".format(value)}", style = MaterialTheme.typography.bodyMedium)
    }
}
