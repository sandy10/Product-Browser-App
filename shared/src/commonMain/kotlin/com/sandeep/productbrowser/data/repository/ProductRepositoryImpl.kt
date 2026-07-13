package com.sandeep.productbrowser.data.repository

import com.sandeep.productbrowser.core.result.ApiResponse
import com.sandeep.productbrowser.data.mapper.toDomain
import com.sandeep.productbrowser.data.remote.datasource.ProductRemoteDataSource
import com.sandeep.productbrowser.domain.model.Product
import com.sandeep.productbrowser.domain.repository.ProductRepository
import kotlin.time.Clock

class ProductRepositoryImpl(
    private val remoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    companion object {
        private const val CACHE_TTL_MS = 5 * 60 * 1000L // 5 minutes
    }

    // In-memory cache for product list
    private var cachedProducts: List<Product>? = null
    private var productsCacheTimestamp: Long = 0L

    // In-memory cache for individual products
    private val cachedProductById: MutableMap<Int, Product> = mutableMapOf()
    private val productCacheTimestamps: MutableMap<Int, Long> = mutableMapOf()

    // In-memory cache for search results
    private val cachedSearchResults: MutableMap<String, List<Product>> = mutableMapOf()
    private val searchCacheTimestamps: MutableMap<String, Long> = mutableMapOf()

    private fun isCacheValid(timestamp: Long): Boolean {
        return (Clock.System.now().toEpochMilliseconds() - timestamp) < CACHE_TTL_MS
    }

    override suspend fun getProducts(): ApiResponse<List<Product>> {

        // Return cached data if valid
        cachedProducts?.let { cached ->
            if (isCacheValid(productsCacheTimestamp)) {
                return ApiResponse.Success(cached)
            }
        }

        return when (val response = remoteDataSource.getProducts()) {

            is ApiResponse.Success -> {
                val products = response.data.products.map { it.toDomain() }
                // Update cache
                cachedProducts = products
                productsCacheTimestamp = Clock.System.now().toEpochMilliseconds()
                // Also cache individual products
                products.forEach { product ->
                    cachedProductById[product.id] = product
                    productCacheTimestamps[product.id] = productsCacheTimestamp
                }
                ApiResponse.Success(products)
            }

            is ApiResponse.Failure -> {
                // Return stale cache as fallback on failure
                cachedProducts?.let { stale ->
                    return ApiResponse.Success(stale)
                }
                response
            }
        }
    }

    override suspend fun searchProducts(
        query: String
    ): ApiResponse<List<Product>> {

        // Return cached search results if valid
        val normalizedQuery = query.trim().lowercase()
        cachedSearchResults[normalizedQuery]?.let { cached ->
            if (isCacheValid(searchCacheTimestamps[normalizedQuery] ?: 0L)) {
                return ApiResponse.Success(cached)
            }
        }

        return when (val response = remoteDataSource.searchProducts(query)) {

            is ApiResponse.Success -> {
                val products = response.data.products.map { it.toDomain() }
                // Update search cache
                cachedSearchResults[normalizedQuery] = products
                searchCacheTimestamps[normalizedQuery] = Clock.System.now().toEpochMilliseconds()
                ApiResponse.Success(products)
            }

            is ApiResponse.Failure -> {
                // Return stale search cache as fallback on failure
                cachedSearchResults[normalizedQuery]?.let { stale ->
                    return ApiResponse.Success(stale)
                }
                response
            }
        }
    }

    override suspend fun getProduct(
        id: Int
    ): ApiResponse<Product> {

        // Return cached product if valid
        cachedProductById[id]?.let { cached ->
            if (isCacheValid(productCacheTimestamps[id] ?: 0L)) {
                return ApiResponse.Success(cached)
            }
        }

        return when (val response = remoteDataSource.getProduct(id)) {

            is ApiResponse.Success -> {
                val product = response.data.toDomain()
                // Update cache
                cachedProductById[product.id] = product
                productCacheTimestamps[product.id] = Clock.System.now().toEpochMilliseconds()
                ApiResponse.Success(product)
            }

            is ApiResponse.Failure -> {
                // Return stale cache as fallback on failure
                cachedProductById[id]?.let { stale ->
                    return ApiResponse.Success(stale)
                }
                response
            }
        }
    }
}