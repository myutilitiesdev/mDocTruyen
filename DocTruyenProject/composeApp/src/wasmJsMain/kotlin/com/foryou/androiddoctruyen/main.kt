package com.foryou.androiddoctruyen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
