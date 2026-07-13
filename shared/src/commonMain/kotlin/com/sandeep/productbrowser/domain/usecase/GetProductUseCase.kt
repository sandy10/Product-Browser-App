package com.sandeep.productbrowser.domain.usecase

import com.sandeep.productbrowser.domain.repository.ProductRepository

class GetProductUseCase(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(
        id: Int
    ) = repository.getProduct(id)
}