package com.sandeep.productbrowser.presentation.productdetail

sealed interface ProductDetailUiEvent {

    data class Load(
        val id: Int
    ) : ProductDetailUiEvent
}