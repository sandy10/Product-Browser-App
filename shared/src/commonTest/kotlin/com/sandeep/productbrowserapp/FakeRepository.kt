package com.sandeep.productbrowserapp

import com.sandeep.productbrowserapp.domain.model.Product
import com.sandeep.productbrowserapp.domain.repository.ProductRepository

class FakeRepository : ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return listOf(
            Product(
                id = 1,
                title = "Test Phone",
                description = "Test Description",
                price = 100.0,
                brand = "Apple",
                rating = 4.5,
                thumbnail = ""
            )
        )
    }

    override suspend fun searchProducts(query: String): List<Product> {
        return getProducts()
    }
}