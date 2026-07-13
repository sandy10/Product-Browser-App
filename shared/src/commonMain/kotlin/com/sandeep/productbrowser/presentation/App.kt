package com.sandeep.productbrowser.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.sandeep.productbrowser.presentation.navigation.AppNavigation
import com.sandeep.productbrowser.presentation.productdetail.ProductDetailViewModel
import com.sandeep.productbrowser.presentation.productlist.ProductListViewModel
import io.kamel.core.config.Core
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.takeFrom
import io.kamel.image.config.LocalKamelConfig
import io.kamel.image.config.imageBitmapDecoder

@Composable
fun App(

    productListViewModel: ProductListViewModel,
    productDetailViewModel: ProductDetailViewModel
) {

    val kamelConfig = KamelConfig {
        takeFrom(KamelConfig.Core)
        imageBitmapDecoder()
    }

    MaterialTheme {
        CompositionLocalProvider(LocalKamelConfig provides kamelConfig) {
            Scaffold { innerPadding ->
                Box(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    AppNavigation(
                        productListViewModel = productListViewModel,
                        productDetailViewModel = productDetailViewModel
                    )
                }
            }
        }
    }
}
