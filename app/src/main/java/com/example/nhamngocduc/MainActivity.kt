package com.example.nhamngocduc

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nhamngocduc.ui.theme.AppTheme
import com.example.nhamngocduc.ui.navigation.nav3.MainNavDisplay
import com.example.nhamngocduc.ui.navigation.nav3.MainViewModel
import com.example.nhamngocduc.util.ThemeMode
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    private var isServiceRunning = false
    @OptIn(ExperimentalPermissionsApi::class)
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
                val viewModel: MainViewModel = koinViewModel()
                val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
                Log.d("Main Activity", "isLoggedIn: $isLoggedIn")

                MainNavDisplay(
                    modifier = Modifier.fillMaxSize(),
                    isLoggedIn = isLoggedIn,
                    themeMode = themeMode,
                    onThemeChange = {
                        themeMode = when (themeMode) {
                            ThemeMode.DARK -> ThemeMode.LIGHT
                            ThemeMode.LIGHT -> ThemeMode.DARK
                        }
                    }
                )
            }
        }
    }
}



