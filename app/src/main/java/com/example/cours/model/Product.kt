package com.example.cours.model

data class Product(
    val id: Int = 0,
    val name: String,
    val type: ProductType,
    val price: Double,
    val quantity: Int
)

enum class ProductType {
    TSHIRT,
    CAP,
    SWEATSHIRT
} 