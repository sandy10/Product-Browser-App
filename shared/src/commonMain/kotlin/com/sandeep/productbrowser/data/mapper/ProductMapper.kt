package com.sandeep.productbrowser.data.mapper

import com.sandeep.productbrowser.data.dto.ProductDto
import com.sandeep.productbrowser.domain.model.Product

fun ProductDto.toDomain(): Product {

    return Product(
        id = id,
        title = title,
        description = description,
        category = category,
        brand = brand.orEmpty(),
        price = price,
        rating = rating,
        thumbnail = thumbnail,
        images = images
    )
}