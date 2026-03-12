package com.sandeep.productbrowserapp.data.api

import com.sandeep.productbrowserapp.data.model.ProductDto
import com.sandeep.productbrowserapp.data.model.ProductResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ProductApiImpl(
    private val client: HttpClient
) : ProductApi {

    override suspend fun getProducts(): List<ProductDto> {

        val response = client
            .get(ApiRoutes.PRODUCTS)
            .body<ProductResponse>()

        return response.products
    }

    override suspend fun searchProducts(query: String): List<ProductDto> {

        val response = client
            .get("${ApiRoutes.SEARCH_PRODUCTS}?q=$query")
            .body<ProductResponse>()

        return response.products
    }
}