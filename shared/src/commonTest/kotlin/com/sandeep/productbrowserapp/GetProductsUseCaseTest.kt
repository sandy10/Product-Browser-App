package com.sandeep.productbrowserapp

import com.sandeep.productbrowserapp.domain.useCase.GetProductsUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class GetProductsUseCaseTest {
    @Test
    fun testProducts() = runTest {

        val repository = FakeRepository()

        val useCase = GetProductsUseCase(repository)

        val result = useCase()

        assertTrue(result.isNotEmpty())
    }
}