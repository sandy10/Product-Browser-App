package com.sandeep.productbrowserapp.domain.repository

import com.sandeep.productbrowserapp.domain.model.Product

interface ProductRepository {

    suspend fun getProducts(): List<Product>

    suspend fun searchProducts(query: String): List<Product>
}