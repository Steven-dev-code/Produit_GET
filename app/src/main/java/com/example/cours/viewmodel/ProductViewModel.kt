package com.example.cours.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cours.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _currentProduct = MutableStateFlow<Product?>(null)
    val currentProduct: StateFlow<Product?> = _currentProduct.asStateFlow()

    fun addProduct(product: Product) {
        viewModelScope.launch {
            val newList = _products.value.toMutableList()
            newList.add(product.copy(id = newList.size + 1))
            _products.value = newList
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            val newList = _products.value.toMutableList()
            val index = newList.indexOfFirst { it.id == product.id }
            if (index != -1) {
                newList[index] = product
                _products.value = newList
            }
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            val newList = _products.value.filter { it.id != product.id }
            _products.value = newList
        }
    }

    fun setCurrentProduct(product: Product?) {
        _currentProduct.value = product
    }
} 