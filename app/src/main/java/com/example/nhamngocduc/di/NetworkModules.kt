package com.example.nhamngocduc.di

import com.example.nhamngocduc.data.remote.api.HomeApiService
import com.example.nhamngocduc.data.remote.api.SongApiService
import com.example.nhamngocduc.util.K
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val RETROFIT_AUDIO = named("retrofit_audio")
val RETROFIT_HOME = named("retrofit_home")

private fun buildClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(K.REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(K.REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(K.REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .build()
}

private fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()
val networkModule = module {
    single { buildClient()}
    single { provideConverterFactory() }
    single(RETROFIT_AUDIO) {
        Retrofit.Builder()
            .baseUrl(K.API_BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }
    single(RETROFIT_HOME) {
        Retrofit.Builder()
            .baseUrl(K.HOME_API_BASE_URL)
            .client(get<OkHttpClient>()) // Uses the shared OkHttpClient
            .addConverterFactory(get<GsonConverterFactory>()) // Uses the shared ConverterFactory
            .build()
    }
    single {
        get<Retrofit>(RETROFIT_HOME).create(HomeApiService::class.java)
    }
    single {
        get<Retrofit>(RETROFIT_AUDIO).create(SongApiService::class.java)
    }
}