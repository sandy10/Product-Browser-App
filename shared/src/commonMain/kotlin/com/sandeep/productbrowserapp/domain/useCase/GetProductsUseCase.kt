package com.sandeep.productbrowserapp.domain.useCase

import com.sandeep.productbrowserapp.domain.model.Product
import com.sandeep.productbrowserapp.domain.repository.ProductRepository

class GetProductsUseCase(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(): List<Product> {
        return repository.getProducts()
    }
}