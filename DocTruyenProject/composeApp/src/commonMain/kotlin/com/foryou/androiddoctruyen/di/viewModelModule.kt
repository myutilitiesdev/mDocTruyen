package com.foryou.androiddoctruyen.di

import com.foryou.androiddoctruyen.presenter.screens.main.tabs.HomeVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeVM(get(), get()) }
}