package com.foryou.androiddoctruyen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import io.github.xxfast.decompose.router.LocalRouterContext
import io.github.xxfast.decompose.router.RouterContext
import io.github.xxfast.decompose.router.defaultRouterContext
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
fun main() {

    initKoin()

    onWasmReady {
        val rootRouterContext: RouterContext = defaultRouterContext()

        ComposeViewport(document.body!!) {
            BoxWithConstraints {
                val windowSizeClass: WindowSizeClass = calculateWindowSizeClass()
                CompositionLocalProvider(
                    LocalRouterContext provides rootRouterContext,
                    LocalWindowSizeClass provides windowSizeClass,
                ) {
                    App()
                }
            }
        }
    }
}