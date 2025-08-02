package com.example.nhamngocduc.di

import android.content.ContentResolver
import com.example.nhamngocduc.data.repository.AudioRepositoryImpl
import com.example.nhamngocduc.domain.repository.AudioRepository
import com.example.nhamngocduc.domain.usecases.playlist.LoadAllSongs
import com.example.nhamngocduc.domain.usecases.playlist.LibraryUseCases
import com.example.nhamngocduc.ui.library.LibraryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LibraryViewModel(get()) }
}

val repositoryModule = module {
    single<ContentResolver> { androidContext().contentResolver }
    single<AudioRepository> { AudioRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory { LoadAllSongs(get()) }
    factory { LibraryUseCases(loadAllSongs = get()) }
}
