package com.sandeep.productbrowserapp.data.repository

import com.sandeep.productbrowserapp.data.api.ProductApi
import com.sandeep.productbrowserapp.domain.model.Product
import com.sandeep.productbrowserapp.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val api: ProductApi
) : ProductRepository {

    override suspend fun getProducts(): List<Product> {

        val response = api.getProducts()

        return response.map {
            Product(
                id = it.id,
                title = it.title,
                description = it.description,
                category = it.category,
                price = it.price,
                brand = it.brand,
                rating = it.rating,
                thumbnail = it.thumbnail
            )
        }
    }

    override suspend fun searchProducts(query: String): List<Product> {

        val response = api.searchProducts(query)

        return response.map {
            Product(
                id = it.id,
                title = it.title,
                description = it.description,
                category = it.category,
                price = it.price,
                brand = it.brand,
                rating = it.rating,
                thumbnail = it.thumbnail
            )
        }
    }
}