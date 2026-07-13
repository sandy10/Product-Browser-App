@file:Suppress("MISSING_DEPENDENCY_SUPERCLASS_IN_TYPE_ARGUMENT")

package com.sandeep.productbrowser.core.network

import io.ktor.client.HttpClient
import com.sandeep.productbrowser.core.common.Constants
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Suppress("MISSING_DEPENDENCY_SUPERCLASS_IN_TYPE_ARGUMENT")
actual object HttpClientFactory {
    @Suppress("MISSING_DEPENDENCY_SUPERCLASS_IN_TYPE_ARGUMENT")
    actual fun create(): HttpClient {
        return HttpClient(OkHttp) {

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = false
                        isLenient = true
                        explicitNulls = false
                        coerceInputValues = true
                    }
                )
            }

            install(HttpTimeout) {
                requestTimeoutMillis = Constants.Timeout.REQUEST
                connectTimeoutMillis = Constants.Timeout.CONNECT
              //  socketTimeoutMillis = Constants.Timeout.SOCKET
            }

            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }
}