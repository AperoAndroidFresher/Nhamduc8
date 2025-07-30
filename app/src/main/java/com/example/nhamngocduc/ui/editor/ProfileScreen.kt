package com.example.nhamngocduc.ui.editor

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nhamngocduc.ui.editor.components.EditScreenBody
import com.example.nhamngocduc.ui.editor.components.EditScreenTopBar
import com.example.nhamngocduc.util.ThemeMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    themeMode: ThemeMode,
    onThemeChange: () -> Unit,
    viewModel: ProfileViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                }) },
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            EditScreenTopBar(
                state = state.value,
                themeMode = themeMode,
                onActionClick = {
                    viewModel.processIntent(ProfileContract.Intent.ToggleEditableMode)
                },
                onThemeModeClick = onThemeChange
            )
        },
        content = {paddingValues ->
            EditScreenBody(
                modifier = Modifier
                    .padding(paddingValues),
                state = state.value,
                onSubmitClick = {
                    viewModel.processIntent(ProfileContract.Intent.SubmitProfile)
                },
                onNameChanged = {
                    viewModel.processIntent(ProfileContract.Intent.ChangeInput(it, InputType.NAME))
                },
                onPhoneChanged = {
                    viewModel.processIntent(ProfileContract.Intent.ChangeInput(it, InputType.PHONE))
                },
                onSchoolNameChanged = {
                    viewModel.processIntent(ProfileContract.Intent.ChangeInput(it, InputType.UNIVERSITY))
                },
                onDescriptionChanged = {
                    viewModel.processIntent(ProfileContract.Intent.ChangeInput(it, InputType.DESCRIPTION))
                },
                onProfilePictureChanged = {
                    viewModel.processIntent(ProfileContract.Intent.ChangeProfilePicture(it))
                },
                onHideDialog = {
                    viewModel.processIntent(ProfileContract.Intent.HideDialog)
                }
            )
        }
    )
}


