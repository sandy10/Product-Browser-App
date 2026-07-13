package com.sandeep.productbrowser.core.common

    object Constants {

        const val BASE_URL = "https://dummyjson.com/"

        object Endpoints {
            const val PRODUCTS = "products"
            const val SEARCH = "products/search"
        }

        object Timeout {
            const val REQUEST = 30_000L
            const val CONNECT = 30_000L
        }
        
        object Strings {
            const val ALL = "All"
            const val BACK = "Back"
            const val SEARCH_PRODUCTS = "Search Products"
            const val RETRY = "Retry"
            const val NO_PRODUCTS_FOUND = "No Products Found"
            const val UNKNOWN_ERROR = "Unknown error"
            const val HELLO = "Hello, "
        }
    }