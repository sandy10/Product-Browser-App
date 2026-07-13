package com.sandeep.productbrowser

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform