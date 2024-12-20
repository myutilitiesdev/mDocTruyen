package com.foryou.androiddoctruyen.di

import com.foryou.androiddoctruyen.datasource.remote.home.HomeRepository
import com.foryou.androiddoctruyen.datasource.remote.story.StoryRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { HomeRepository(get()) }
    single { StoryRepository(get()) }
}