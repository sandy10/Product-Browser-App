package com.sandeep.productbrowser.domain.usecase

import com.sandeep.productbrowser.core.result.ApiResponse
import com.sandeep.productbrowser.domain.model.Product
import com.sandeep.productbrowser.domain.repository.ProductRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetProductsUseCaseTest {

    private val fakeRepository = object : ProductRepository {
        var productsToReturn: ApiResponse<List<Product>> = ApiResponse.Success(emptyList())

        override suspend fun getProducts(): ApiResponse<List<Product>> {
            return productsToReturn
        }

        override suspend fun searchProducts(query: String): ApiResponse<List<Product>> {
            return ApiResponse.Success(emptyList())
        }

        override suspend fun getProduct(id: Int): ApiResponse<Product> {
            return ApiResponse.Failure(com.sandeep.productbrowser.core.result.AppError.NotFound)
        }
    }

    private val getProductsUseCase = GetProductsUseCase(fakeRepository)

    @Test
    fun `invoke should return products from repository`() = runTest {
        // Arrange
        val expectedProducts = listOf(
            Product(
                id = 1,
                title = "Test Product",
                description = "Description",
                category = "Category",
                brand = "Brand",
                price = 9.99,
                rating = 4.5,
                thumbnail = "thumb.jpg",
                images = listOf("image1.jpg")
            )
        )
        fakeRepository.productsToReturn = ApiResponse.Success(expectedProducts)

        // Act
        val result = getProductsUseCase()

        // Assert
        assertTrue(result is ApiResponse.Success)
        assertEquals(expectedProducts, (result as ApiResponse.Success).data)
    }
}
