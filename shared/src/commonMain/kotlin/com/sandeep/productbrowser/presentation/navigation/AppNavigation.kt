package com.sandeep.productbrowser.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sandeep.productbrowser.presentation.product.ProductDetailScreen
import com.sandeep.productbrowser.presentation.product.ProductListScreen
import com.sandeep.productbrowser.presentation.productdetail.ProductDetailViewModel
import com.sandeep.productbrowser.presentation.productlist.ProductListViewModel
import kotlinx.serialization.Serializable

@Serializable
data object ProductListRoute

@Serializable
data class ProductDetailRoute(val productId: Int)

@Composable
fun AppNavigation(
    productListViewModel: ProductListViewModel,
    productDetailViewModel: ProductDetailViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ProductListRoute
    ) {

        composable<ProductListRoute> {
            ProductListScreen(
                viewModel = productListViewModel,
                onProductClick = { id ->
                    navController.navigate(ProductDetailRoute(id))
                }
            )
        }

        composable<ProductDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<ProductDetailRoute>()

            ProductDetailScreen(
                viewModel = productDetailViewModel,
                productId = route.productId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}