package com.example.nhamngocduc.ui.editor

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import com.example.nhamngocduc.ui.editor.components.EditScreenBody
import com.example.nhamngocduc.ui.editor.components.EditScreenTopBar
import com.example.nhamngocduc.util.ThemeMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    themeMode: ThemeMode,
    onThemeChange: () -> Unit
) {
    var isEditable by rememberSaveable {
        mutableStateOf(false)
    }

    var name by rememberSaveable {
        mutableStateOf("")
    }

    var phoneNumber by rememberSaveable {
        mutableStateOf("")
    }

    var schoolName by rememberSaveable {
        mutableStateOf("")
    }

    var description by rememberSaveable {
        mutableStateOf("")
    }

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
                isEditable = isEditable,
                themeMode = themeMode,
                onActionClick = {
                    isEditable = true
                },
                onThemeModeClick = onThemeChange
            )
        },
        content = {
            EditScreenBody(
                modifier = Modifier
                    .padding(it),
                name = name,
                phoneNumber = phoneNumber,
                schoolName = schoolName,
                description = description,
                isEditable = isEditable,
                onSubmitClick = {
                    isEditable = false
                },
                onNameChanged = {
                    name = it
                },
                onPhoneChanged = {
                    phoneNumber = it
                },
                onSchoolNameChanged = {
                    schoolName = it
                },
                onDescriptionChanged = {
                    description = it
                },
            )
        }
    )
}


