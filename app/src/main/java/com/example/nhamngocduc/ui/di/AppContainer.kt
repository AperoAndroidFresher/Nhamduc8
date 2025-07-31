package com.example.nhamngocduc.ui.di

import android.content.Context
import com.example.nhamngocduc.data.repository.AudioRepositoryImpl
import com.example.nhamngocduc.domain.repository.AudioRepository

interface AppContainer {
    val audioRepository: AudioRepository
}

class DefaultAppContainer(
    private val context: Context
) : AppContainer {
    override val audioRepository: AudioRepository by lazy {
        AudioRepositoryImpl(context.contentResolver)
    }
}