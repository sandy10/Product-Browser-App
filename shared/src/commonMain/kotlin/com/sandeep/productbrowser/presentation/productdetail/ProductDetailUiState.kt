package com.sandeep.productbrowser.presentation.productdetail

import com.sandeep.productbrowser.domain.model.Product

data class ProductDetailUiState(

    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String? = null
)