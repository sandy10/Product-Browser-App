package com.sandeep.productbrowser.core.util

actual object Logger {
    actual fun d(tag: String, message: String) {
        println("DEBUG: [$tag] $message")
    }

    actual fun e(tag: String, message: String) {
        println("DEBUG: [$tag] $message")
    }
}