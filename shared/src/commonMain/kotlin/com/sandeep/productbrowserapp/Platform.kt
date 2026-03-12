package com.sandeep.productbrowserapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform