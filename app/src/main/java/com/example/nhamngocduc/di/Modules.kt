package com.example.nhamngocduc.di

import android.content.ContentResolver
import androidx.room.Room
import com.example.nhamngocduc.data.local.room.AppDatabase
import com.example.nhamngocduc.data.local.model.mapper.MusicMapper
import com.example.nhamngocduc.data.local.model.mapper.PlaylistMapper
import com.example.nhamngocduc.data.local.model.mapper.PlaylistWithSongsMapper
import com.example.nhamngocduc.data.local.model.mapper.SongWithPlaylistsMapper
import com.example.nhamngocduc.data.local.model.mapper.UserMapper
import com.example.nhamngocduc.data.local.model.mapper.UserWithDetailsMapper
import com.example.nhamngocduc.data.remote.mapper.SongDtoMapper
import com.example.nhamngocduc.data.repository.AudioRepositoryImpl
import com.example.nhamngocduc.data.repository.MusicRepositoryImpl
import com.example.nhamngocduc.data.repository.PlaylistRepositoryImpl
import com.example.nhamngocduc.data.repository.RelationRepositoryImpl
import com.example.nhamngocduc.data.repository.SongNetworkRepositoryImpl
import com.example.nhamngocduc.data.repository.UserRepositoryImpl
import com.example.nhamngocduc.domain.manager.SessionManager
import com.example.nhamngocduc.domain.repository.AudioRepository
import com.example.nhamngocduc.domain.repository.MusicRepository
import com.example.nhamngocduc.domain.repository.PlaylistRepository
import com.example.nhamngocduc.domain.repository.RelationRepository
import com.example.nhamngocduc.domain.repository.SongNetworkRepository
import com.example.nhamngocduc.domain.repository.UserRepository
import com.example.nhamngocduc.domain.usecases.library.GetSongPlaylistLink
import com.example.nhamngocduc.domain.usecases.library.LoadAllSongs
import com.example.nhamngocduc.domain.usecases.library.LibraryUseCases
import com.example.nhamngocduc.domain.usecases.library.LoadSongsFromRemote
import com.example.nhamngocduc.domain.usecases.music.GetAllSongs
import com.example.nhamngocduc.domain.usecases.music.GetPlaylistsFromSong
import com.example.nhamngocduc.domain.usecases.music.GetSongByLocalId
import com.example.nhamngocduc.domain.usecases.music.GetSongByRemoteId
import com.example.nhamngocduc.domain.usecases.music.InsertSong
import com.example.nhamngocduc.domain.usecases.music.SongUseCases
import com.example.nhamngocduc.domain.usecases.playlist.AddNewPlaylist
import com.example.nhamngocduc.domain.usecases.playlist.AddSongToPlaylist
import com.example.nhamngocduc.domain.usecases.playlist.GetAllPlaylists
import com.example.nhamngocduc.domain.usecases.playlist.GetPlaylist
import com.example.nhamngocduc.domain.usecases.playlist.GetSongsFromPlaylist
import com.example.nhamngocduc.domain.usecases.playlist.PlaylistUseCases
import com.example.nhamngocduc.domain.usecases.playlist.RemovePlaylist
import com.example.nhamngocduc.domain.usecases.playlist.RemoveSongFromPlaylist
import com.example.nhamngocduc.domain.usecases.playlist.RenamePlaylist
import com.example.nhamngocduc.domain.usecases.user.GetAllUsername
import com.example.nhamngocduc.domain.usecases.user.GetUser
import com.example.nhamngocduc.domain.usecases.user.GetUserPlaylists
import com.example.nhamngocduc.domain.usecases.user.InsertUser
import com.example.nhamngocduc.domain.usecases.user.UpdateProfileAtomically
import com.example.nhamngocduc.domain.usecases.user.UserUseCases
import com.example.nhamngocduc.ui.library.LibraryViewModel
import com.example.nhamngocduc.ui.login_signup.login.LoginViewModel
import com.example.nhamngocduc.ui.login_signup.signup.SignupViewModel
import com.example.nhamngocduc.ui.playlist.whole.PlaylistWholeViewModel
import com.example.nhamngocduc.ui.profile.ProfileViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single { SessionManager() }

}
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

    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
    single<PlaylistRepository> { PlaylistRepositoryImpl(get(), get(),
        get(), get(), get()) }
    single<MusicRepository> { MusicRepositoryImpl(get(), get(), get()) }
    single<RelationRepository> { RelationRepositoryImpl(get()) }
    single<SongNetworkRepository> { SongNetworkRepositoryImpl(get(), get()) }
}

val mapperModule = module {
    single { UserMapper() }
    single { PlaylistMapper() }
    single { MusicMapper() }

    single { UserWithDetailsMapper(get(), get()) }
    single { SongWithPlaylistsMapper(get(), get()) }
    single { PlaylistWithSongsMapper(get(), get()) }

    single { SongDtoMapper() }
}

val useCaseModule = module {
    factory { LoadAllSongs(get()) }
    factory { GetSongPlaylistLink(get()) }
    factory { LoadSongsFromRemote(get()) }
    factory { LibraryUseCases(get(), get(), get()) }

    factory { AddNewPlaylist(get()) }
    factory { AddSongToPlaylist(get()) }
    factory { GetAllPlaylists(get()) }
    factory { GetPlaylist(get()) }
    factory { RemovePlaylist(get()) }
    factory { RemoveSongFromPlaylist(get()) }
    factory { RenamePlaylist(get()) }
    factory { GetSongsFromPlaylist(get()) }
    factory { PlaylistUseCases(
        get(),
        get(),
        get(),
        get(),
        get(),
        get(),
        get(),
        get()
    ) }

    factory { InsertSong(get()) }
    factory { GetAllSongs(get()) }
    factory { GetPlaylistsFromSong(get()) }
    factory { GetSongByRemoteId(get()) }
    factory { GetSongByLocalId(get()) }
    factory { SongUseCases(
        get(),
        get(),
        get(),
        get(),
        get()
    ) }

    factory { GetAllUsername(get()) }
    factory { GetUser(get()) }
    factory { InsertUser(get()) }
    factory { GetUserPlaylists(get()) }
    factory { UpdateProfileAtomically(get()) }
    factory { UserUseCases(
        get(),
        get(),
        get(),
        get(),
        get()
    ) }
}
val viewModelModule = module {
    viewModel { LibraryViewModel(get(), get(), get()) }
    viewModel { PlaylistWholeViewModel(get(), get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
}


