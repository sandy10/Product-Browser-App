package com.sandeep.productbrowser.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sandeep.productbrowser.presentation.product.ProductDetailScreen
import com.sandeep.productbrowser.presentation.product.ProductListScreen
import com.sandeep.productbrowser.presentation.productdetail.ProductDetailViewModel
import com.sandeep.productbrowser.presentation.productlist.ProductListViewModel

@Composable
fun AppNavigation(

    productListViewModel: ProductListViewModel,
    productDetailViewModel: ProductDetailViewModel

) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.ProductList.route

    ) {

        composable(
            route = Screen.ProductList.route

        ) {

            ProductListScreen(
                viewModel = productListViewModel,

                onProductClick = {
                    navController.navigate(
                        Screen.ProductDetail.createRoute(it)
                    )
                }
            )
        }

        composable(
            route = Screen.ProductDetail.route,

            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                }
            )
        ) {

            val productId =
                it.arguments?.getString("productId")?.toIntOrNull() ?: 0

            ProductDetailScreen(
                viewModel = productDetailViewModel,
                productId = productId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}