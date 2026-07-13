package com.sandeep.productbrowser.presentation.productlist

sealed interface ProductListUiEvent {

    data object LoadProducts : ProductListUiEvent
    data class Search(
        val query: String
    ) : ProductListUiEvent
}