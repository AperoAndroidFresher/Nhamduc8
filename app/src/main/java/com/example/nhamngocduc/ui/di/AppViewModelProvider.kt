package com.example.nhamngocduc.ui.di

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nhamngocduc.NhamNgocDucApplication
import com.example.nhamngocduc.ui.playlist.PlaylistViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            PlaylistViewModel(
                audioRepository = nhamNgocDucApplication().container.audioRepository
            )
        }
    }
}

fun CreationExtras.nhamNgocDucApplication(): NhamNgocDucApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as NhamNgocDucApplication)


