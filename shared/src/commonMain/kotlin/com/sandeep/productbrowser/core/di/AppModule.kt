package com.sandeep.productbrowser.core.di

import com.sandeep.productbrowser.core.dispatcher.DefaultDispatcherProvider
import com.sandeep.productbrowser.core.network.HttpClientFactory
import com.sandeep.productbrowser.data.remote.api.api.ProductApi
import com.sandeep.productbrowser.data.remote.api.ProductApiImpl
import com.sandeep.productbrowser.data.remote.datasource.ProductRemoteDataSource
import com.sandeep.productbrowser.data.repository.ProductRepositoryImpl
import com.sandeep.productbrowser.domain.usecase.GetProductUseCase
import com.sandeep.productbrowser.domain.usecase.GetProductsUseCase
import com.sandeep.productbrowser.domain.usecase.SearchProductsUseCase
import com.sandeep.productbrowser.presentation.productdetail.ProductDetailViewModel
import com.sandeep.productbrowser.presentation.productlist.ProductListViewModel

object AppModule {

    val dispatcherProvider by lazy {
        DefaultDispatcherProvider()
    }

    val httpClient by lazy {
        HttpClientFactory.create()
    }

    val productApi: ProductApi by lazy {
        ProductApiImpl(httpClient)
    }

    val remoteDataSource by lazy {
        ProductRemoteDataSource(productApi)
    }

    val repository by lazy {
        ProductRepositoryImpl(remoteDataSource)
    }

    val getProductsUseCase by lazy {
        GetProductsUseCase(repository)
    }

    val searchProductsUseCase by lazy {
        SearchProductsUseCase(repository)
    }

    val getProductUseCase by lazy {
        GetProductUseCase(repository)
    }

    fun provideProductListViewModel(): ProductListViewModel {
        return ProductListViewModel(
            getProductsUseCase,
            searchProductsUseCase
        )
    }

    fun provideProductDetailViewModel(): ProductDetailViewModel {
        return ProductDetailViewModel(
            getProductUseCase
        )
    }
}