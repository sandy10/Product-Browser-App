package com.sandeep.productbrowser.presentation.navigation

import androidx.navigation.NavHostController

class AppNavigator(
    private val navController: NavHostController
) {

    fun openProduct(id: Int) {

        navController.navigate(
            Screen.ProductDetail.createRoute(id)
        )
    }

    fun back() {

        navController.popBackStack()
    }
}