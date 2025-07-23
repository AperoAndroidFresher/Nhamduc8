package com.example.nhamngocduc.ui.editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.nhamngocduc.ui.editor.components.EditScreenDialog
import com.example.nhamngocduc.ui.editor.components.EditTextSector
import com.example.nhamngocduc.ui.editor.components.ProfileImage
import com.example.nhamngocduc.ui.editor.components.SubmitButton
import kotlinx.coroutines.delay

@Composable
fun EditScreenBody(
    modifier: Modifier = Modifier,
    name: String,
    phoneNumber: String,
    schoolName: String,
    description: String,
    isEditable: Boolean,
    onSubmitClick: () -> Unit,
    onNameChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onSchoolNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit
) {
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var showDialogContent by rememberSaveable {
        mutableStateOf(false)
    }

    var submitCondition by rememberSaveable {
        mutableStateOf(false)
    }
    submitCondition = checkSubmitCondition(name, schoolName, phoneNumber)

    LaunchedEffect(showDialog) {
        if (showDialog) {
            showDialogContent = true
            delay(1500L)
            showDialogContent = false
            delay(500L)
            showDialog = false
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            ProfileImage(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                size = 128.dp,
                borderColor = Color.Black
            )
            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                EditTextSector(
                    modifier = Modifier.weight(1f),
                    isError = name.filterNot { it.isLetter() }.count() > 0,
                    errorText = "*Only accepts letter",
                    value = name,
                    sectorLabelText = "NAME",
                    placeholderText = "Enter your name...",
                    enabled = !isEditable,
                    onValueChanged = onNameChanged,
                )

                EditTextSector(
                    modifier = Modifier.weight(1f),
                    isError = !phoneNumber.isDigitsOnly(),
                    keyboardType = KeyboardType.Number,
                    errorText = "*Only accepts number",
                    value = phoneNumber,
                    sectorLabelText = "PHONE NUMBER",
                    placeholderText = "Your phone number...",
                    enabled = !isEditable,
                    onValueChanged = onPhoneChanged,
                )
            }

            EditTextSector(
                modifier = Modifier.fillMaxWidth(),
                isError = schoolName.filterNot { it.isLetter() }.count() > 0,
                errorText = "*Only accepts letter",
                value = schoolName,
                sectorLabelText = "UNIVERSITY NAME",
                placeholderText = "Your university name...",
                enabled = !isEditable,
                onValueChanged = onSchoolNameChanged,
            )

            EditTextSector(
                modifier = Modifier.fillMaxWidth(),
                imeAction = ImeAction.Done,
                value = description,
                minLines = 8,
                maxLines = 8,
                sectorLabelText = "DESCRIBE YOURSELF",
                placeholderText = "Enter a description about yourself...",
                enabled = !isEditable,
                onValueChanged = onDescriptionChanged
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = isEditable,
                    enter = slideInVertically{
                            it -> it
                    },
                    exit = scaleOut(tween(500)),
                ) {
                    SubmitButton(
                        submitCondition = submitCondition,
                        onSubmitClick =  {
                            onSubmitClick()
                            showDialog = true
                        }
                    )
                }
            }
        }

        EditScreenDialog(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            showDialog = showDialog,
            visibleDialogContent = showDialogContent,
        )
    }
}

private fun checkPhoneNumberCondition(phoneNumber: String) = phoneNumber.isDigitsOnly() && phoneNumber.length in 10..15

private fun checkUniversityNameCondition(universityName: String) = universityName.length > 3 && universityName.none {it.isDigit()}

private fun checkUsernameCondition(name: String) = name.none {it.isDigit()} && name.length in 1..30

private fun checkSubmitCondition(name: String, universityName: String, phoneNumber: String): Boolean {
    return checkUsernameCondition(name)
            && checkUniversityNameCondition(universityName)
            && checkPhoneNumberCondition(phoneNumber)
}