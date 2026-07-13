package com.sandeep.productbrowser.presentation.productlist

import com.sandeep.productbrowser.domain.model.Product

data class ProductListUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val error: String? = null
)