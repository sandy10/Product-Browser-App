package com.sandeep.productbrowser.core.network

import com.sandeep.productbrowser.core.common.Constants
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual object HttpClientFactory {

    actual fun create(): HttpClient {

        return HttpClient(Darwin) {

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
                socketTimeoutMillis = Constants.Timeout.SOCKET
            }

            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }
}