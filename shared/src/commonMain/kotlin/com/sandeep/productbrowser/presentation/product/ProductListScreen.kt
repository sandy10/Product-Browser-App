package com.sandeep.productbrowser.presentation.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sandeep.productbrowser.presentation.components.*
import com.sandeep.productbrowser.presentation.productlist.ProductListUiEvent
import com.sandeep.productbrowser.presentation.productlist.ProductListViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

        if (state.categories.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = state.selectedCategory == null,
                        onClick = { viewModel.onEvent(ProductListUiEvent.SelectCategory(null)) },
                        label = { Text("All") }
                    )
                }
                items(state.categories) { category ->
                    FilterChip(
                        selected = state.selectedCategory == category,
                        onClick = { viewModel.onEvent(ProductListUiEvent.SelectCategory(category)) },
                        label = { Text(category.replaceFirstChar { it.uppercase() }) }
                    )
                }
            }
        }

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