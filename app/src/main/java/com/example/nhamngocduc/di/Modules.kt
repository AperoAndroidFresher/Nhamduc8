package com.example.nhamngocduc.di

import android.content.ContentResolver
import androidx.room.Room
import com.example.nhamngocduc.data.local.room.AppDatabase
import com.example.nhamngocduc.data.model.mapper_impl.MusicMapper
import com.example.nhamngocduc.data.model.mapper_impl.PlaylistMapper
import com.example.nhamngocduc.data.model.mapper_impl.PlaylistWithSongsMapper
import com.example.nhamngocduc.data.model.mapper_impl.SongWithPlaylistsMapper
import com.example.nhamngocduc.data.model.mapper_impl.UserMapper
import com.example.nhamngocduc.data.model.mapper_impl.UserWithDetailsMapper
import com.example.nhamngocduc.data.repository.AudioRepositoryImpl
import com.example.nhamngocduc.data.repository.MusicRepositoryImpl
import com.example.nhamngocduc.data.repository.PlaylistRepositoryImpl
import com.example.nhamngocduc.data.repository.RelationRepositoryImpl
import com.example.nhamngocduc.data.repository.UserRepositoryImpl
import com.example.nhamngocduc.domain.repository.AudioRepository
import com.example.nhamngocduc.domain.repository.MusicRepository
import com.example.nhamngocduc.domain.repository.PlaylistRepository
import com.example.nhamngocduc.domain.repository.RelationRepository
import com.example.nhamngocduc.domain.repository.UserRepository
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
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "nhamngocduc-db"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().musicDao() }
    single { get<AppDatabase>().playlistDao() }
    single { get<AppDatabase>().relationshipDao() }
}

val repositoryModule = module {
    single<ContentResolver> { androidContext().contentResolver }
    single<AudioRepository> { AudioRepositoryImpl(get()) }

    single<UserRepository> { UserRepositoryImpl(get()) }
    single<PlaylistRepository> { PlaylistRepositoryImpl(get()) }
    single<MusicRepository> { MusicRepositoryImpl(get()) }
    single<RelationRepository> { RelationRepositoryImpl(get()) }
}

val mapperModule = module {
    single { UserMapper() }
    single { PlaylistMapper() }
    single { MusicMapper() }

    single { UserWithDetailsMapper(get(), get()) }
    single { PlaylistWithSongsMapper(get(), get()) }
    single { SongWithPlaylistsMapper(get(), get()) }
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
val viewModelModule = module {
    viewModel { LibraryViewModel(get(), get()) }
    viewModel { PlaylistWholeViewModel(get()) }
}


