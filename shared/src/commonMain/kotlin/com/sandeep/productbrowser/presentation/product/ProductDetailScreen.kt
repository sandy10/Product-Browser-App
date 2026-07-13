package com.sandeep.productbrowser.presentation.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sandeep.productbrowser.presentation.components.*
import com.sandeep.productbrowser.presentation.productdetail.ProductDetailUiEvent
import com.sandeep.productbrowser.presentation.productdetail.ProductDetailViewModel

@Composable
fun ProductDetailScreen(

    viewModel: ProductDetailViewModel,
    productId: Int,
    onBack: () -> Unit

) {

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(productId) {
        viewModel.onEvent(
            ProductDetailUiEvent.Load(productId)
        )

    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                TextButton(
                    onClick = onBack
                ) {
                    Text("Back")
                }
            }
        }
    ) { padding ->
        when {
            state.isLoading -> {
                LoadingView()
            }
            state.error != null -> {
                ErrorView(
                    message = state.error!!,
                    onRetry = {
                        viewModel.onEvent(
                            ProductDetailUiEvent.Load(productId)
                        )
                    }
                )

            }

            state.product != null -> {
                val product = state.product!!
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {

                    ProductImage(
                        images = product.images
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                    Text(
                        text = product.brand,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                    Text(
                        text = product.category,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    RatingBar(
                        rating = product.rating
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    PriceView(
                        price = product.price
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            else -> {
                EmptyView()
            }
        }
    }
}