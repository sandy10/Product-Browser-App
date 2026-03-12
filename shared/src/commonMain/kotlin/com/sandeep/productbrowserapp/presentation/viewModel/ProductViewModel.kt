package com.sandeep.productbrowserapp.presentation.viewModel

import com.sandeep.productbrowserapp.domain.model.Product
import com.sandeep.productbrowserapp.domain.useCase.GetProductsUseCase
import com.sandeep.productbrowserapp.domain.useCase.SearchProductsUseCase
import com.sandeep.productbrowserapp.presentation.state.ProductUiState
import com.sandeep.productbrowserapp.util.AppConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel(
    private val getProducts: GetProductsUseCase,
    private val searchProducts: SearchProductsUseCase
) {

    private val _state = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val state: StateFlow<ProductUiState> = _state

    private var allProducts: List<Product> = emptyList()

    var selectedCategory: String? = null
        private set


    suspend fun loadProducts() {

        try {

            val products = getProducts()
            allProducts = products
            _state.value = ProductUiState.Success(products)

        } catch (e: Exception) {
            e.printStackTrace()
            _state.value =
                ProductUiState.Error(e.message ?: AppConstants.UNKNOWN_ERROR)
        }
    }
    suspend fun search(query: String) {

        try {
            if (query.isBlank()) {
                _state.value = ProductUiState.Success(allProducts)
                return
            }

            val products = searchProducts(query)
            _state.value = ProductUiState.Success(products)

        } catch (e: Exception) {
            e.printStackTrace()
            _state.value =
                ProductUiState.Error(e.message ?: AppConstants.SEARCH_FAILED)
        }
    }

    fun filterByCategory(category: String) {

        if (category == AppConstants.CATEGORY_ALL) {
            selectedCategory = null
            _state.value = ProductUiState.Success(allProducts)
            return
        }

        if (selectedCategory == category) {
            selectedCategory = null
            _state.value = ProductUiState.Success(allProducts)
            return
        }

        selectedCategory = category

        val filtered = allProducts.filter {
            it.category.equals(category, ignoreCase = true)
        }

        _state.value = ProductUiState.Success(filtered)

    }

    fun getCategories(): List<String> {
        return listOf(AppConstants.CATEGORY_ALL) + allProducts.map { it.category }.distinct()
    }
}