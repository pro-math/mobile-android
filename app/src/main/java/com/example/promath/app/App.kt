package com.example.promath.app

import android.app.Application
import android.content.Context
import com.example.promath.di.appModule
import com.example.promath.di.dataModule
import com.example.promath.di.domainModule
import com.example.promath.di.generationOfExamplesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.binds
import org.koin.dsl.module

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            module {
                single { this@App } binds arrayOf(Context::class, Application::class)
            }
            androidLogger(level = Level.DEBUG)
            modules(listOf(appModule, domainModule, dataModule, generationOfExamplesModule))
            androidContext(applicationContext)
        }

    }

}