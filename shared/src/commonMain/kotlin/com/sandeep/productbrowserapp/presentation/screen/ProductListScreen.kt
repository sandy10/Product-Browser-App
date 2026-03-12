package com.sandeep.productbrowserapp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sandeep.productbrowserapp.domain.model.Product

@Composable
fun ProductListScreen(
    products: List<Product>,
    onProductClick: (Product) -> Unit
) {

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(8.dp)
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
                        //.clickable { onProductClick(product) }
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