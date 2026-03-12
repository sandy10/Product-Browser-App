package com.sandeep.productbrowserapp

import androidx.compose.runtime.*
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

@Composable
@Preview
fun App() {

    val viewModel = remember {
        ProductViewModel(
            AppModule.getProductsUseCase,
            AppModule.searchProductsUseCase
        )
    }

    val coroutineScope = rememberCoroutineScope()
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
                categories = viewModel.getCategories(),
                selectedCategory = viewModel.selectedCategory,
                onProductClick = {
                    selectedProduct = it
                },
                onSearch = { query ->
                    coroutineScope.launch {
                        viewModel.search(query)
                    }
                },
                onCategoryFilter = { category ->
                    viewModel.filterByCategory(category)
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
