package com.example.nhamngocduc.di

import android.content.ContentResolver
import com.example.nhamngocduc.data.repository.AudioRepositoryImpl
import com.example.nhamngocduc.domain.repository.AudioRepository
import com.example.nhamngocduc.domain.usecases.playlist.LoadAllSongs
import com.example.nhamngocduc.domain.usecases.playlist.PlaylistUseCases
import com.example.nhamngocduc.ui.playlist.PlaylistViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ContentResolver> { androidContext().contentResolver }

    single<AudioRepository> { AudioRepositoryImpl(get()) }

    factory { LoadAllSongs(get()) }
    factory { PlaylistUseCases(loadAllSongs = get()) }

    viewModel { PlaylistViewModel(get()) }
}