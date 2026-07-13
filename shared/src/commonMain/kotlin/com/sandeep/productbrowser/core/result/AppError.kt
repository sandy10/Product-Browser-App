package com.sandeep.productbrowser.core.result

import com.sandeep.productbrowser.core.common.Constants.Strings

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

fun AppError.toUserMessage(): String = when (this) {
    is AppError.NoInternet -> Strings.ERROR_NO_INTERNET
    is AppError.Timeout -> Strings.ERROR_TIMEOUT
    is AppError.Unauthorized -> Strings.ERROR_UNAUTHORIZED
    is AppError.Forbidden -> Strings.ERROR_FORBIDDEN
    is AppError.NotFound -> Strings.ERROR_NOT_FOUND
    is AppError.ServerError -> Strings.ERROR_SERVER
    is AppError.Serialization -> Strings.ERROR_SERIALIZATION
    is AppError.Unknown -> message
}