package com.example.cours.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cours.model.Product
import com.example.cours.model.ProductType
import com.example.cours.viewmodel.ProductViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState

data class FormState(
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
    val type: ProductType = ProductType.TSHIRT,
    val nameError: String? = null,
    val priceError: String? = null,
    val quantityError: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormScreen(
    viewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onSave: (Product) -> Unit,
    onCancel: () -> Unit
) {
    val currentProduct by viewModel.currentProduct.collectAsState()
    var formState by remember {
        mutableStateOf(
            FormState(
                name = currentProduct?.name ?: "",
                price = currentProduct?.price?.toString() ?: "",
                quantity = currentProduct?.quantity?.toString() ?: "",
                type = currentProduct?.type ?: ProductType.TSHIRT
            )
        )
    }
    var showDropdown by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (currentProduct == null) "Ajouter un produit" else "Modifier le produit",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = formState.name,
            onValueChange = { 
                formState = formState.copy(
                    name = it,
                    nameError = if (it.isEmpty()) "Le nom est requis" else null
                )
            },
            label = { Text("Nom du produit") },
            isError = formState.nameError != null,
            supportingText = formState.nameError?.let {
                { 
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, "Erreur", tint = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.width(4.dp))
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = showDropdown,
            onExpandedChange = { showDropdown = it }
        ) {
            OutlinedTextField(
                value = formState.type.name,
                onValueChange = {},
                readOnly = true,
                label = { Text("Type de produit") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDropdown) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = showDropdown,
                onDismissRequest = { showDropdown = false }
            ) {
                ProductType.values().forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type.name) },
                        onClick = {
                            formState = formState.copy(type = type)
                            showDropdown = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = formState.price,
            onValueChange = { 
                formState = formState.copy(
                    price = it,
                    priceError = when {
                        it.isEmpty() -> "Le prix est requis"
                        it.toDoubleOrNull() == null -> "Le prix doit être un nombre"
                        it.toDoubleOrNull()!! < 0 -> "Le prix ne peut pas être négatif"
                        else -> null
                    }
                )
            },
            label = { Text("Prix") },
            isError = formState.priceError != null,
            supportingText = formState.priceError?.let {
                { 
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, "Erreur", tint = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.width(4.dp))
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = formState.quantity,
            onValueChange = { 
                formState = formState.copy(
                    quantity = it,
                    quantityError = when {
                        it.isEmpty() -> "La quantité est requise"
                        it.toIntOrNull() == null -> "La quantité doit être un nombre entier"
                        it.toIntOrNull()!! < 0 -> "La quantité ne peut pas être négative"
                        else -> null
                    }
                )
            },
            label = { Text("Quantité") },
            isError = formState.quantityError != null,
            supportingText = formState.quantityError?.let {
                { 
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, "Erreur", tint = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.width(4.dp))
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = {
                    viewModel.setCurrentProduct(null)
                    onCancel()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Annuler")
            }
            Button(
                onClick = {
                    if (isFormValid(formState)) {
                        val product = Product(
                            id = currentProduct?.id ?: 0,
                            name = formState.name,
                            type = formState.type,
                            price = formState.price.toDoubleOrNull() ?: 0.0,
                            quantity = formState.quantity.toIntOrNull() ?: 0
                        )
                        if (currentProduct != null) {
                            viewModel.updateProduct(product)
                        } else {
                            viewModel.addProduct(product)
                        }
                        viewModel.setCurrentProduct(null)
                        onSave(product)
                    }
                },
                enabled = isFormValid(formState),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (currentProduct == null) "Ajouter" else "Modifier")
            }
        }
    }
}

private fun isFormValid(state: FormState): Boolean {
    return state.nameError == null && state.name.isNotEmpty() &&
           state.priceError == null && state.price.isNotEmpty() &&
           state.quantityError == null && state.quantity.isNotEmpty()
} 