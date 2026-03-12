package com.sandeep.productbrowserapp.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.sandeep.productbrowserapp.domain.model.Product
import com.sandeep.productbrowserapp.util.AppConstants
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun ProductDetailScreen(
    product: Product,
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Button(
            onClick = onBackClick,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(AppConstants.BACK)
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            elevation = 4.dp
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                KamelImage(
                    resource = asyncPainterResource(product.thumbnail),
                    contentDescription = product.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = product.title,
                    style = MaterialTheme.typography.h5
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = AppConstants.BRAND + " ${product.brand}",
                    style = MaterialTheme.typography.subtitle1
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = AppConstants.PRICE + " $${product.price}",
                    style = MaterialTheme.typography.h6
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = AppConstants.RATING + " ${product.rating}"
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = AppConstants.DESCRIPTION,
                    style = MaterialTheme.typography.subtitle1
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product.description
                )
            }
        }
    }
}