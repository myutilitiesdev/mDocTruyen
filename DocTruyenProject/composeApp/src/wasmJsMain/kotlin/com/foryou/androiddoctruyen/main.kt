package com.foryou.androiddoctruyen

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.foryou.androiddoctruyen.presenter.screens.main.App
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    initKoin()

    ComposeViewport(document.body!!) {
        App()
    }
}
