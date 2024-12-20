package com.foryou.androiddoctruyen.di

fun appModule() =
    listOf(commonModule, platformModule, repositoryModule, KtorModule, viewModelModule)