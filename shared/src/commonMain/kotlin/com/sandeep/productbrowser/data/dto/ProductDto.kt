package com.sandeep.productbrowser.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(

    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val brand: String? = null,
    val thumbnail: String? = null,
    val images: List<String>? = null
)