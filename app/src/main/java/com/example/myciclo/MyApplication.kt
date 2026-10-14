package com.example.myciclo

import android.app.Application
import com.example.myciclo.di.AppContainer

class MyApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
