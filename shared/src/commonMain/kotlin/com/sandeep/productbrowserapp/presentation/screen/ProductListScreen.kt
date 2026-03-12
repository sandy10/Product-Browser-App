package com.sandeep.productbrowserapp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sandeep.productbrowserapp.domain.model.Product

@Composable
fun ProductListScreen(
    products: List<Product>,
    categories: List<String>,
    selectedCategory: String?,
    onProductClick: (Product) -> Unit,
    onSearch: (String) -> Unit,
    onCategoryFilter: (String) -> Unit
) {

    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        SearchBar(
            query = query,
            onQueryChange = {
                query = it
                onSearch(it)
            }
        )

        CategoryFilter(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                onCategoryFilter(category)
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)

        ) {

            items(products) { product ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onProductClick(product) },
                    elevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = product.title,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.body1
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "$${product.price}",
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}