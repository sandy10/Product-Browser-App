package com.sandeep.productbrowser.domain.usecase

data class ProductUseCases(

    val getProducts: GetProductsUseCase,

    val searchProducts: SearchProductsUseCase,

    val getProduct: GetProductUseCase

)