package com.sandeep.productbrowser.core.result

sealed interface AppError {

    data object NoInternet : AppError
    data object Timeout : AppError
    data object Unauthorized : AppError
    data object Forbidden : AppError
    data object NotFound : AppError
    data object ServerError : AppError
    data object Serialization : AppError
    data class Unknown(
        val message: String
    ) : AppError
}