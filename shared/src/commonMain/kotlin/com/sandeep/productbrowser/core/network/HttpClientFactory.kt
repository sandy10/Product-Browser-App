package com.sandeep.productbrowser.core.network

import io.ktor.client.HttpClient


expect object HttpClientFactory {
    fun create(): HttpClient
}