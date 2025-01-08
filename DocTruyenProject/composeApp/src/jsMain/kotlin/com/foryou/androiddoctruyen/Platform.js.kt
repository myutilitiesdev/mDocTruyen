package com.foryou.androiddoctruyen

class JSPlatform : Platform {
    override val name: String = "Web with JSBrowser"
}

actual fun getPlatform(): Platform = JSPlatform()