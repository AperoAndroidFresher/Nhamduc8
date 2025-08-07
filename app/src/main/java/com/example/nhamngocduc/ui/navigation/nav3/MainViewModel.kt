package com.example.nhamngocduc.ui.navigation.nav3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhamngocduc.domain.manager.SessionManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    sessionManager: SessionManager
) : ViewModel() {
    val isLoggedIn: StateFlow<Boolean?> = sessionManager.currentUsername
        .map { username ->
            username.isNotEmpty()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )
}