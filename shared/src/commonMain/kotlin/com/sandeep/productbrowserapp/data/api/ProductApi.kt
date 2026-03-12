package com.sandeep.productbrowserapp.data.api

import com.sandeep.productbrowserapp.data.model.ProductDto

interface ProductApi {

    suspend fun getProducts(): List<ProductDto>

    suspend fun searchProducts(query: String): List<ProductDto>
}