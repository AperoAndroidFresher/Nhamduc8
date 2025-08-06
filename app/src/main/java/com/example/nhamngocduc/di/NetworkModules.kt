package com.example.nhamngocduc.di

import com.example.nhamngocduc.data.remote.api.SongApiService
import com.example.nhamngocduc.util.K
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private fun buildClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(K.REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(K.REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(K.REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .build()
}

private fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()

private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(K.API_BASE_URL)
        .client(buildClient())
        .addConverterFactory(provideConverterFactory())
        .build()
}

fun provideSongApiService(retrofit: Retrofit): SongApiService =
    retrofit.create(SongApiService::class.java)

val networkModule = module {
    single { buildClient()}
    single { provideConverterFactory() }
    single { provideRetrofit() }
    single { provideSongApiService(get()) }
}