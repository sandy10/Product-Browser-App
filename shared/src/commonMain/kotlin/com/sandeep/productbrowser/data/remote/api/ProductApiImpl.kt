package com.sandeep.productbrowser.data.remote.api

import com.sandeep.productbrowser.core.common.Constants
import com.sandeep.productbrowser.data.dto.ProductDto
import com.sandeep.productbrowser.data.dto.ProductResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ProductApiImpl(
    private val client: HttpClient
) : ProductApi {

    override suspend fun getProducts(): ProductResponseDto {

        return client.get(
            Constants.BASE_URL + Constants.Endpoints.PRODUCTS
        ) {
            parameter("limit", 0)
        }.body()
    }

    override suspend fun searchProducts(
        query: String
    ): ProductResponseDto {

        return client.get(
            Constants.BASE_URL + Constants.Endpoints.SEARCH
        ) {
            parameter("q", query)
        }.body()
    }

    override suspend fun getProduct(
        id: Int
    ): ProductDto {

        return client.get(
            "${Constants.BASE_URL}${Constants.Endpoints.PRODUCTS}/$id"
        ).body()
    }
}