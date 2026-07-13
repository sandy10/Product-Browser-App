package com.sandeep.productbrowser.domain.repository

import com.sandeep.productbrowser.core.result.ApiResponse
import com.sandeep.productbrowser.domain.model.Product

interface ProductRepository {

    suspend fun getProducts(): ApiResponse<List<Product>>

    suspend fun searchProducts(
        query: String
    ): ApiResponse<List<Product>>

    suspend fun getProduct(
        id: Int
    ): ApiResponse<Product>
}