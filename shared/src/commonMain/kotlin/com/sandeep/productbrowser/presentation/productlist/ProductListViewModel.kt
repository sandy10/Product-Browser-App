package com.sandeep.productbrowser.presentation.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandeep.productbrowser.core.dispatcher.DispatcherProvider
import com.sandeep.productbrowser.core.result.ApiResponse
import com.sandeep.productbrowser.core.result.toUserMessage
import com.sandeep.productbrowser.domain.model.Product
import com.sandeep.productbrowser.domain.usecase.GetProductsUseCase
import com.sandeep.productbrowser.domain.usecase.SearchProductsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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
    private val searchProductsUseCase: SearchProductsUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private val searchQuery = MutableStateFlow("")
    private var allLoadedProducts: List<Product> = emptyList()

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
                    }.flowOn(dispatcherProvider.io)
                }
                .collect { result ->
                    if (result == null) {
                        loadProducts()
                    } else {
                        handleApiResponse(result)
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
            is ProductListUiEvent.SelectCategory -> {
                val newCategory = if (_uiState.value.selectedCategory == event.category) null else event.category
                val filteredProducts = if (newCategory == null) {
                    allLoadedProducts
                } else {
                    allLoadedProducts.filter { it.category == newCategory }
                }

                _uiState.value = _uiState.value.copy(
                    selectedCategory = newCategory,
                    products = filteredProducts
                )
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            val result = getProductsUseCase()
            handleApiResponse(result)
        }
    }

    private fun handleApiResponse(result: ApiResponse<List<Product>>) {
        when (result) {
            is ApiResponse.Success -> {
                allLoadedProducts = result.data
                val categories = allLoadedProducts.map { it.category }.distinct()
                
                _uiState.value = _uiState.value.copy(
                    products = allLoadedProducts,
                    categories = categories,
                    selectedCategory = null,
                    isLoading = false,
                    error = null
                )
            }
            is ApiResponse.Failure -> {
                _uiState.value = _uiState.value.copy(
                    error = result.error.toUserMessage(),
                    isLoading = false
                )
            }
        }
    }
}