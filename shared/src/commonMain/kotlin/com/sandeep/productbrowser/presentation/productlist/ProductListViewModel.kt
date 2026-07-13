package com.sandeep.productbrowser.presentation.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandeep.productbrowser.core.result.ApiResponse
import com.sandeep.productbrowser.domain.usecase.GetProductsUseCase
import com.sandeep.productbrowser.domain.usecase.SearchProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    init {
        loadProducts()
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    flow {
                        if (query.isBlank()) {
                            emit(null)
                        } else {
                            emit(searchProductsUseCase(query))
                        }
                    }.flowOn(Dispatchers.IO)
                }
                .collect { result ->
                    if (result == null) {
                        loadProducts()
                    } else {
                        when (result) {
                            is ApiResponse.Success -> {
                                _uiState.value = _uiState.value.copy(
                                    products = result.data,
                                    error = null,
                                    isLoading = false
                                )
                            }
                            is ApiResponse.Failure -> {
                                _uiState.value = _uiState.value.copy(
                                    error = result.error.toString(),
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
        }
    }

    fun onEvent(event: ProductListUiEvent) {
        when (event) {
            ProductListUiEvent.LoadProducts -> loadProducts()
            is ProductListUiEvent.Search -> {
                searchQuery.value = event.query
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            when (val result = getProductsUseCase()) {
                is ApiResponse.Success -> {
                    _uiState.value = ProductListUiState(
                        products = result.data,
                        isLoading = false
                    )
                }
                is ApiResponse.Failure -> {
                    _uiState.value = ProductListUiState(
                        error = result.error.toString(),
                        isLoading = false
                    )
                }
            }
        }
    }
}