package com.sandeep.productbrowser.core.common

    object Constants {

        const val BASE_URL = "https://dummyjson.com/"

        object Endpoints {
            const val PRODUCTS = "docs/products"
            const val SEARCH = "docs/products/search"
        }

        object Timeout {
            const val REQUEST = 30_000L
            const val CONNECT = 30_000L
            const val SOCKET = 30_000L
        }
    }