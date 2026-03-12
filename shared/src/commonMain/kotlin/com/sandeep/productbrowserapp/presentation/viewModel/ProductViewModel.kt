package com.sandeep.productbrowserapp.presentation.viewModel

import com.sandeep.productbrowserapp.domain.useCase.GetProductsUseCase
import com.sandeep.productbrowserapp.domain.useCase.SearchProductsUseCase
import com.sandeep.productbrowserapp.presentation.state.ProductUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel(
    private val getProducts: GetProductsUseCase,
    private val searchProducts: SearchProductsUseCase
) {

    private val _state = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val state: StateFlow<ProductUiState> = _state

    suspend fun loadProducts() {

        try {

            val products = getProducts()

            _state.value = ProductUiState.Success(products)

        } catch (e: Exception) {
            e.printStackTrace()
            _state.value =
                ProductUiState.Error(e.message ?: "Unknown error")
        }
    }
    suspend fun search(query: String) {

        val products = searchProducts(query)
        _state.value = ProductUiState.Success(products)
    }
}