package com.sandeep.productbrowser.domain.usecase

import com.sandeep.productbrowser.domain.repository.ProductRepository

class SearchProductsUseCase(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(
        query: String
    ) = repository.searchProducts(query)
}