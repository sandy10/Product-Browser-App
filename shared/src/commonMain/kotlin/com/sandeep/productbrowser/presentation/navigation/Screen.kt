package com.sandeep.productbrowser.presentation.navigation

sealed class Screen(
    val route: String
) {

    data object ProductList : Screen(
        route = "product_list"
    )

    data object ProductDetail : Screen(
        route = "product_detail/{productId}"
    ) {

        fun createRoute(
            productId: Int
        ): String {
            return "product_detail/$productId"
        }
    }
}