package com.sandeep.productbrowser.data.remote.api

import com.sandeep.productbrowser.data.dto.ProductDto
import com.sandeep.productbrowser.data.dto.ProductResponseDto

interface ProductApi {

    suspend fun getProducts(): ProductResponseDto

    suspend fun searchProducts(
        query: String
    ): ProductResponseDto

    suspend fun getProduct(
        id: Int
    ): ProductDto
}