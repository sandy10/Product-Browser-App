package com.sandeep.productbrowser

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.sandeep.productbrowser.core.di.AppModule
import com.sandeep.productbrowser.presentation.App

fun MainViewController() = ComposeUIViewController {
    val productListViewModel = remember { AppModule.provideProductListViewModel() }
    val productDetailViewModel = remember { AppModule.provideProductDetailViewModel() }

    App(
        productListViewModel = productListViewModel,
        productDetailViewModel = productDetailViewModel
    )
}