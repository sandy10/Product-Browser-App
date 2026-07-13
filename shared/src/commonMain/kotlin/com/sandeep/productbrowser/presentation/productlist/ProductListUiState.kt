package com.sandeep.productbrowser.presentation.productlist

import com.sandeep.productbrowser.domain.model.Product

data class ProductListUiState(

    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null
)