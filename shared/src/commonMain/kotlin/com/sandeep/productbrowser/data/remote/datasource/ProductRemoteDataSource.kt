package com.sandeep.productbrowser.data.remote.datasource

import com.sandeep.productbrowser.core.network.safeApiCall
import com.sandeep.productbrowser.core.result.ApiResponse
import com.sandeep.productbrowser.data.dto.ProductDto
import com.sandeep.productbrowser.data.dto.ProductResponseDto
import com.sandeep.productbrowser.data.remote.api.api.ProductApi

class ProductRemoteDataSource(
    private val api: ProductApi
) {

    suspend fun getProducts(): ApiResponse<ProductResponseDto> =
        safeApiCall {
            api.getProducts()
        }

    suspend fun searchProducts(
        query: String
    ): ApiResponse<ProductResponseDto> =
        safeApiCall {
            api.searchProducts(query)
        }

    suspend fun getProduct(
        id: Int
    ): ApiResponse<ProductDto> =
        safeApiCall {
            api.getProduct(id)
        }
}