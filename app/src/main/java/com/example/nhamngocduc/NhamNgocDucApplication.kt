package com.example.nhamngocduc

import android.app.Application
import com.example.nhamngocduc.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NhamNgocDucApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NhamNgocDucApplication)
            modules(appModule)
        }
    }


}