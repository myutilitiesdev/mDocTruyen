package com.foryou.androiddoctruyen

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.foryou.androiddoctruyen.presenter.screens.main.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DocTruyenProject",
    ) {
        App()
    }
}