package com.sandeep.productbrowser.core.network

import com.sandeep.productbrowser.core.result.ApiResponse
import com.sandeep.productbrowser.core.result.AppError
import io.ktor.client.plugins.*
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.SerializationException
import com.sandeep.productbrowser.core.common.Constants

suspend inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> T
): ApiResponse<T> {

    return try {

        ApiResponse.Success(apiCall())

    } catch (_: HttpRequestTimeoutException) {

        ApiResponse.Failure(AppError.Timeout)

    } catch (_: TimeoutCancellationException) {

        ApiResponse.Failure(AppError.Timeout)

    } catch (_: ClientRequestException) {

        ApiResponse.Failure(AppError.NotFound)

    } catch (_: ServerResponseException) {

        ApiResponse.Failure(AppError.ServerError)

    } catch (_: SerializationException) {

        ApiResponse.Failure(AppError.Serialization)

    } catch (e: Exception) {

        ApiResponse.Failure(
            AppError.Unknown(e.message ?: Constants.Strings.UNKNOWN_ERROR)
        )
    }
}