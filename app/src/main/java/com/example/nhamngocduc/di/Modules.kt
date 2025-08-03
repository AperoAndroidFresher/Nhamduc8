package com.example.nhamngocduc.di

import android.content.ContentResolver
import com.example.nhamngocduc.data.repository.AudioRepositoryImpl
import com.example.nhamngocduc.data.repository.PlaylistRepositoryImpl
import com.example.nhamngocduc.domain.repository.AudioRepository
import com.example.nhamngocduc.domain.repository.PlaylistRepository
import com.example.nhamngocduc.domain.usecases.library.LoadAllSongs
import com.example.nhamngocduc.domain.usecases.library.LibraryUseCases
import com.example.nhamngocduc.domain.usecases.playlist.AddNewPlaylist
import com.example.nhamngocduc.domain.usecases.playlist.AddSongToPlaylist
import com.example.nhamngocduc.domain.usecases.playlist.GetAllPlaylists
import com.example.nhamngocduc.domain.usecases.playlist.GetPlaylist
import com.example.nhamngocduc.domain.usecases.playlist.PlaylistUseCases
import com.example.nhamngocduc.domain.usecases.playlist.RemovePlaylist
import com.example.nhamngocduc.domain.usecases.playlist.RemoveSongFromPlaylist
import com.example.nhamngocduc.domain.usecases.playlist.RenamePlaylist
import com.example.nhamngocduc.ui.library.LibraryViewModel
import com.example.nhamngocduc.ui.playlist.whole.PlaylistWholeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LibraryViewModel(get(), get()) }
    viewModel { PlaylistWholeViewModel(get()) }
}

val repositoryModule = module {
    single<ContentResolver> { androidContext().contentResolver }
    single<AudioRepository> { AudioRepositoryImpl(get()) }
    single<PlaylistRepository> { PlaylistRepositoryImpl() }
}

val useCaseModule = module {
    factory { LoadAllSongs(get()) }
    factory { LibraryUseCases(loadAllSongs = get()) }

    factory { AddNewPlaylist(get()) }
    factory { AddSongToPlaylist(get()) }
    factory { GetAllPlaylists(get()) }
    factory { GetPlaylist(get()) }
    factory { RemovePlaylist(get()) }
    factory { RemoveSongFromPlaylist(get()) }
    factory { RenamePlaylist(get()) }
    factory { PlaylistUseCases(
        get(),
        get(),
        get(),
        get(),
        get(),
        get(),
        get()
    ) }
}
