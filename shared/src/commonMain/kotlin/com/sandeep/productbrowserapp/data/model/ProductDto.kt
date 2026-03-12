package com.sandeep.productbrowserapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val brand: String = "",
    val rating: Double = 0.0,
    val thumbnail: String = ""
)