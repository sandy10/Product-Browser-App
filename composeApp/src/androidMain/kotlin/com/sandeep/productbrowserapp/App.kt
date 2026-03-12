package com.sandeep.productbrowserapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sandeep.productbrowserapp.di.AppModule
import com.sandeep.productbrowserapp.domain.model.Product
import com.sandeep.productbrowserapp.presentation.screen.ErrorScreen
import com.sandeep.productbrowserapp.presentation.screen.LoadingScreen
import com.sandeep.productbrowserapp.presentation.screen.ProductDetailScreen
import com.sandeep.productbrowserapp.presentation.screen.ProductListScreen
import com.sandeep.productbrowserapp.presentation.state.ProductUiState
import com.sandeep.productbrowserapp.presentation.viewModel.ProductViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

import productbrowserapp.composeapp.generated.resources.Res
import productbrowserapp.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {

    val viewModel = remember {
        ProductViewModel(
            AppModule.getProductsUseCase,
            AppModule.searchProductsUseCase
        )
    }

    val state by viewModel.state.collectAsState()
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(Unit) {
            viewModel.loadProducts()
    }

    when (val currentState = state) {

        is ProductUiState.Loading -> {
            LoadingScreen()
        }

        is ProductUiState.Success -> {
            if (selectedProduct == null) {
            ProductListScreen(
                products = currentState.products,
                onProductClick = {
                    selectedProduct = it
                }
            )
            } else {

                ProductDetailScreen(
                    product = selectedProduct!!,
                    onBackClick = {
                        selectedProduct = null
                    }
                )
            }
        }

        is ProductUiState.Error -> {
            ErrorScreen()
        }
    }
}
