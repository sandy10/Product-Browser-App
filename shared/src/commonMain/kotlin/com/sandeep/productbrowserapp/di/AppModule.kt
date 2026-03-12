package com.sandeep.productbrowserapp.di

import com.sandeep.productbrowserapp.data.api.ProductApiImpl
import com.sandeep.productbrowserapp.data.repository.ProductRepositoryImpl
import com.sandeep.productbrowserapp.domain.useCase.GetProductsUseCase
import com.sandeep.productbrowserapp.domain.useCase.SearchProductsUseCase
import com.sandeep.productbrowserapp.network.KtorClient
import com.sandeep.productbrowserapp.presentation.viewModel.ProductViewModel

object AppModule {

    private val client = KtorClient.client

    private val api = ProductApiImpl(client)

    private val repository = ProductRepositoryImpl(api)

    val getProductsUseCase = GetProductsUseCase(repository)

    val searchProductsUseCase = SearchProductsUseCase(repository)

    fun provideProductViewModel(): ProductViewModel {
        return ProductViewModel(
            getProductsUseCase,
            searchProductsUseCase
        )
    }
}