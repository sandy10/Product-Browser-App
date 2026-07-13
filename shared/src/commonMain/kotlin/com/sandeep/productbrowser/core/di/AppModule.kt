package com.sandeep.productbrowser.core.di

import com.sandeep.productbrowser.core.dispatcher.DefaultDispatcherProvider
import com.sandeep.productbrowser.core.network.HttpClientFactory

object AppModule {

    val dispatcherProvider by lazy {
        DefaultDispatcherProvider()
    }

    val httpClient by lazy {
        HttpClientFactory.create()
    }
}