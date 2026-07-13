package com.sandeep.productbrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import com.sandeep.productbrowser.core.di.AppModule
import com.sandeep.productbrowser.presentation.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = true
        super.onCreate(savedInstanceState)

        setContent {
            val productListViewModel =
                remember {
                    AppModule.provideProductListViewModel()
                }

            val productDetailViewModel =
                remember {
                    AppModule.provideProductDetailViewModel()
                }
            App(
                productListViewModel,
                productDetailViewModel
            )
        }
    }
}