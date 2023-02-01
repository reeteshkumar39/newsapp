package com.example.demo.views

import android.app.Application
import com.example.demo.views.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            modules(listOf(apiModule, viewModelModule, repositoryModule, retrofitModule, dbModule, itemDecorator))
        }
    }
}