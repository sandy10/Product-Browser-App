package com.sandeep.productbrowserapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val products: List<ProductDto> = emptyList(),
    val total: Int = 0,
    val skip: Int = 0,
    val limit: Int = 0
)