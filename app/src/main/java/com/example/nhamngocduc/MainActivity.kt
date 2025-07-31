package com.example.nhamngocduc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nhamngocduc.ui.di.AppViewModelProvider
import com.example.nhamngocduc.ui.theme.AppTheme
import com.example.nhamngocduc.ui.navigation.nav3.MainNavDisplay
import com.example.nhamngocduc.ui.playlist.PlaylistScreen
import com.example.nhamngocduc.ui.playlist.PlaylistViewModel
import com.example.nhamngocduc.util.ThemeMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            var themeMode by remember {
                mutableStateOf(ThemeMode.DARK)
            }

            AppTheme(
                themeMode = themeMode
            ) {
                val playlistViewModel: PlaylistViewModel = viewModel(factory = AppViewModelProvider.Factory)

                MainNavDisplay(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = playlistViewModel,
                    themeMode = themeMode,
                    onThemeChange = {
                        themeMode = when(themeMode) {
                            ThemeMode.DARK -> ThemeMode.LIGHT
                            ThemeMode.LIGHT -> ThemeMode.DARK
                        }
                    }
                )
            }
        }
    }
}