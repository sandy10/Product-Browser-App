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
            
            // Error messages
            const val ERROR_NO_INTERNET = "No internet connection. Please check your network and try again."
            const val ERROR_TIMEOUT = "The request timed out. Please try again."
            const val ERROR_UNAUTHORIZED = "You are not authorized to access this resource."
            const val ERROR_FORBIDDEN = "Access to this resource is forbidden."
            const val ERROR_NOT_FOUND = "The requested resource was not found."
            const val ERROR_SERVER = "A server error occurred. Please try again later."
            const val ERROR_SERIALIZATION = "Failed to process the server response."
        }
    }