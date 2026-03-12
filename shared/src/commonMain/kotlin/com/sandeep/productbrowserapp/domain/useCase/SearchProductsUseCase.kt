package com.sandeep.productbrowserapp.domain.useCase

import com.sandeep.productbrowserapp.domain.model.Product
import com.sandeep.productbrowserapp.domain.repository.ProductRepository

class SearchProductsUseCase(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(query: String): List<Product> {
        return repository.searchProducts(query)
    }
}