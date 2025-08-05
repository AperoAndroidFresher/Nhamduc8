package com.example.nhamngocduc

import android.app.Application
import com.example.nhamngocduc.di.appModule
import com.example.nhamngocduc.di.databaseModule
import com.example.nhamngocduc.di.mapperModule
import com.example.nhamngocduc.di.repositoryModule
import com.example.nhamngocduc.di.useCaseModule
import com.example.nhamngocduc.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NhamNgocDucApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NhamNgocDucApplication)
            modules(
                appModule,
                databaseModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
                mapperModule
            )
        }
    }


}