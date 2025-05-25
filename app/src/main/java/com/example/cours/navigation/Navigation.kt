package com.example.cours.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cours.screens.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cours.viewmodel.ProductViewModel

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Accueil")
    object Products : Screen("products", "Produits")
    object AddProduct : Screen("add_product", "Ajouter")
    object EditProduct : Screen("edit_product", "Modifier")
    object Settings : Screen("settings", "Paramètres")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val productViewModel: ProductViewModel = viewModel()
    
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            
            composable(Screen.Products.route) {
                ProductListScreen(
                    viewModel = productViewModel,
                    onAddProduct = { navController.navigate(Screen.AddProduct.route) },
                    onEditProduct = { product -> 
                        navController.navigate(Screen.EditProduct.route)
                    },
                    onDeleteProduct = { }
                )
            }
            
            composable(Screen.AddProduct.route) {
                ProductFormScreen(
                    viewModel = productViewModel,
                    onSave = { _ ->
                        navController.navigateUp()
                    },
                    onCancel = { navController.navigateUp() }
                )
            }
            
            composable(Screen.EditProduct.route) {
                ProductFormScreen(
                    viewModel = productViewModel,
                    onSave = { _ ->
                        navController.navigateUp()
                    },
                    onCancel = { navController.navigateUp() }
                )
            }
            
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(0) }
    
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Accueil") },
            label = { Text(Screen.Home.title) },
            selected = selectedItem == 0,
            onClick = { 
                selectedItem = 0
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            }
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Produits") },
            label = { Text(Screen.Products.title) },
            selected = selectedItem == 1,
            onClick = { 
                selectedItem = 1
                navController.navigate(Screen.Products.route) {
                    popUpTo(Screen.Products.route) { inclusive = true }
                }
            }
        )
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Paramètres") },
            label = { Text(Screen.Settings.title) },
            selected = selectedItem == 2,
            onClick = { 
                selectedItem = 2
                navController.navigate(Screen.Settings.route) {
                    popUpTo(Screen.Settings.route) { inclusive = true }
                }
            }
        )
    }
} 