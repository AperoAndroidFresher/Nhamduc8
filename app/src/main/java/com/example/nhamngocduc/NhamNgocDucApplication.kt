package com.example.nhamngocduc

import android.app.Application
import com.example.nhamngocduc.ui.di.AppContainer
import com.example.nhamngocduc.ui.di.DefaultAppContainer

class NhamNgocDucApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}