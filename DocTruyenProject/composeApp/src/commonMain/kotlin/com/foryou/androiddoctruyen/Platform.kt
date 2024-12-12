package com.foryou.androiddoctruyen

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform