package com.sandeep.productbrowser.presentation.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandeep.productbrowser.core.result.ApiResponse
import com.sandeep.productbrowser.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun onEvent(event: ProductDetailUiEvent) {

        when (event) {
            is ProductDetailUiEvent.Load -> {
                load(event.id)
            }
        }
    }

    private fun load(id: Int) {

        viewModelScope.launch {
            _uiState.value = ProductDetailUiState(
                isLoading = true
            )

            when (val result = getProductUseCase(id)) {
                is ApiResponse.Success -> {
                    _uiState.value = ProductDetailUiState(
                        product = result.data
                    )
                }

                is ApiResponse.Failure -> {
                    _uiState.value = ProductDetailUiState(
                        error = result.error.toString()
                    )
                }
            }
        }
    }
}