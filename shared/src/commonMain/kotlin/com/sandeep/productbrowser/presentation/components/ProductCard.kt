package com.sandeep.productbrowser.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sandeep.productbrowser.domain.model.Product
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun ProductCard(
    product: Product,
    onClick: (Int) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onClick(product.id)
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            if (product.thumbnail.isNotBlank()) {
                KamelImage(
                    resource = asyncPainterResource(data = product.thumbnail),
                    contentDescription = product.title,
                    modifier = Modifier
                        .size(80.dp),
                    contentScale = ContentScale.Fit,
                    onLoading = {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    onFailure = {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No Image", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Image", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductCardPreview() {
    MaterialTheme {
        ProductCard(
            product = Product(
                id = 1,
                title = "Sample Product",
                description = "This is a great product.",
                category = "electronics",
                price = 299.99,
                rating = 4.5,
                brand = "Acme",
                thumbnail = "https://example.com/image.jpg",
                images = emptyList()
            ),
            onClick = {}
        )
    }
}