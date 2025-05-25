package com.example.cours.validation

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

object ProductValidation {
    fun validateName(name: String): ValidationResult {
        return when {
            name.isEmpty() -> ValidationResult(false, "Le nom est requis")
            name.length < 3 -> ValidationResult(false, "Le nom doit contenir au moins 3 caractères")
            name.length > 50 -> ValidationResult(false, "Le nom ne doit pas dépasser 50 caractères")
            else -> ValidationResult(true)
        }
    }

    fun validatePrice(price: String): ValidationResult {
        return when {
            price.isEmpty() -> ValidationResult(false, "Le prix est requis")
            price.toDoubleOrNull() == null -> ValidationResult(false, "Le prix doit être un nombre valide")
            price.toDouble() < 0 -> ValidationResult(false, "Le prix ne peut pas être négatif")
            price.toDouble() > 1000 -> ValidationResult(false, "Le prix ne peut pas dépasser 1000€")
            else -> ValidationResult(true)
        }
    }

    fun validateQuantity(quantity: String): ValidationResult {
        return when {
            quantity.isEmpty() -> ValidationResult(false, "La quantité est requise")
            quantity.toIntOrNull() == null -> ValidationResult(false, "La quantité doit être un nombre entier")
            quantity.toInt() < 0 -> ValidationResult(false, "La quantité ne peut pas être négative")
            quantity.toInt() > 1000 -> ValidationResult(false, "La quantité ne peut pas dépasser 1000")
            else -> ValidationResult(true)
        }
    }
} 