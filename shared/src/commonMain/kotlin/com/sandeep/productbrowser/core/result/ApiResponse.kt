package com.sandeep.productbrowser.core.result

sealed interface ApiResponse<out T> {

    data class Success<T>(
        val data: T
    ) : ApiResponse<T>

    data class Failure(
        val error: AppError
    ) : ApiResponse<Nothing>
}