package com.foryou.androiddoctruyen

import android.app.Application
import com.foryou.androiddoctruyen.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@MyApp)
            // declare modules
            modules(appModule())
        }

        Timber.plant(Timber.DebugTree())

        Timber.d("onCreate")
    }
}