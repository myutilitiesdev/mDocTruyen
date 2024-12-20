package com.foryou.androiddoctruyen

import com.foryou.androiddoctruyen.di.appModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}