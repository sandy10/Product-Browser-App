package com.sandeep.productbrowser.presentation.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.sandeep.productbrowser.presentation.components.*
import com.sandeep.productbrowser.presentation.productlist.ProductListUiEvent
import com.sandeep.productbrowser.presentation.productlist.ProductListViewModel

@Composable
fun ProductListScreen(

    viewModel: ProductListViewModel,
    onProductClick: (Int) -> Unit

) {

    val state by viewModel.uiState.collectAsState()
    var query by remember {
        mutableStateOf("")

    }

    Column {

        SearchBar(
            value = query,
            onValueChange = {
                query = it
                viewModel.onEvent(
                    ProductListUiEvent.Search(it)
                )
            },
        )

        when {
            state.isLoading -> {
                LoadingView()
            }

            state.error != null -> {
                ErrorView(
                    message = state.error!!,
                    onRetry = {
                        viewModel.onEvent(
                            ProductListUiEvent.LoadProducts
                        )
                    }
                )
            }

            state.products.isEmpty() -> {
                EmptyView()
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        state.products
                    ) {
                        ProductCard(
                            product = it,
                            onClick = onProductClick
                        )
                    }
                }
            }
        }
    }
}