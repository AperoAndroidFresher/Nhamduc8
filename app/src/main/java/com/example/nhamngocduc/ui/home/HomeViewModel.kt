package com.example.nhamngocduc.ui.home

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhamngocduc.domain.manager.SessionManager
import com.example.nhamngocduc.domain.model.User
import com.example.nhamngocduc.domain.usecases.home.GetHomeDataUseCase
import com.example.nhamngocduc.domain.usecases.user.UserUseCases
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeDataUseCase: GetHomeDataUseCase,
    private val userUseCases: UserUseCases,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeContract.State>(HomeContract.State.Loading)
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<HomeContract.Event>()
    val event = _event.asSharedFlow()

    private val scope = viewModelScope

    val username = sessionManager.currentUsername
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ""
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val userProfileFlow: Flow<User?> = username.flatMapLatest {
            username -> userUseCases.getUser(username)
    }

    var profilePicture: Uri? by mutableStateOf(null)
    init {
        scope.launch {
            userProfileFlow.collect { user ->
                user?.let {
                    profilePicture = user.profileImage
                }
            }
        }
        processIntent(HomeContract.Intent.LoadHomeData)
    }

    fun processIntent(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.LoadHomeData -> loadData()
        }
    }

    fun processEvent(event: HomeContract.Event) {
        scope.launch {
           when(event) {
               is HomeContract.Event.NavigateToTopAlbums -> _event.emit(event)
               is HomeContract.Event.NavigateToTopTracks -> _event.emit(event)
               is HomeContract.Event.NavigateToTopArtists -> _event.emit(event)
               is HomeContract.Event.NavigateToProfile -> _event.emit(event)
               is HomeContract.Event.NavigateToSettings -> _event.emit(event)
           }
        }
    }

    private fun loadData() {
        scope.launch {
            _uiState.value = HomeContract.State.Loading
            delay(1500L)

            val result = getHomeDataUseCase()
            result.onSuccess { (albums, artists, tracks) ->
                _uiState.value = HomeContract.State.Success(albums, artists, tracks)
            }.onFailure { exception ->
                _uiState.value = HomeContract.State.Error(exception.message ?: "Error")
            }
        }
    }
}