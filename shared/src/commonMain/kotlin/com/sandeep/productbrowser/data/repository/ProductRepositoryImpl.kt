package com.sandeep.productbrowser.data.repository

import com.sandeep.productbrowser.core.result.ApiResponse
import com.sandeep.productbrowser.data.mapper.toDomain
import com.sandeep.productbrowser.data.remote.datasource.ProductRemoteDataSource
import com.sandeep.productbrowser.domain.model.Product
import com.sandeep.productbrowser.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val remoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override suspend fun getProducts(): ApiResponse<List<Product>> {

        return when (val response = remoteDataSource.getProducts()) {

            is ApiResponse.Success -> {
                ApiResponse.Success(
                    response.data.products.map { it.toDomain() }
                )
            }

            is ApiResponse.Failure -> response
        }
    }

    override suspend fun searchProducts(
        query: String
    ): ApiResponse<List<Product>> {

        return when (val response = remoteDataSource.searchProducts(query)) {

            is ApiResponse.Success -> {
                ApiResponse.Success(
                    response.data.products.map { it.toDomain() }
                )
            }

            is ApiResponse.Failure -> response
        }
    }

    override suspend fun getProduct(
        id: Int
    ): ApiResponse<Product> {

        return when (val response = remoteDataSource.getProduct(id)) {

            is ApiResponse.Success -> {
                ApiResponse.Success(
                    response.data.toDomain()
                )
            }

            is ApiResponse.Failure -> response
        }
    }
}