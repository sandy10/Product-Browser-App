package com.sandeep.productbrowser.domain.usecase

import com.sandeep.productbrowser.domain.repository.ProductRepository

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke() =
        repository.getProducts()
}