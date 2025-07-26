package com.example.nhamngocduc.ui.editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.nhamngocduc.ui.editor.components.EditScreenDialog
import com.example.nhamngocduc.ui.editor.components.EditTextSector
import com.example.nhamngocduc.ui.editor.components.ProfileImage
import com.example.nhamngocduc.util.Checker
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

    var nameSubmitCondition by rememberSaveable {
        mutableStateOf(true)
    }

    var schoolNameSubmitCondition by rememberSaveable {
        mutableStateOf(true)
    }

    var phoneNumberSubmitCondition by rememberSaveable {
        mutableStateOf(true)
    }

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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ProfileImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                size = 128.dp,
                borderColor = MaterialTheme.colorScheme.primary
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
                    isNotValid = !nameSubmitCondition,
                    errorText = "*Only accepts letter",
                    invalidText = "At least 3 characters",
                    value = name,
                    sectorLabelText = "NAME",
                    placeholderText = "Enter your name...",
                    enabled = !isEditable,
                    onValueChanged = {
                        onNameChanged(it)
                        nameSubmitCondition = true
                    },
                )

                EditTextSector(
                    modifier = Modifier.weight(1f),
                    isError = !phoneNumber.isDigitsOnly(),
                    isNotValid = !phoneNumberSubmitCondition,
                    keyboardType = KeyboardType.Number,
                    errorText = "*Only accepts number",
                    invalidText = "Length is between 10 and 15",
                    value = phoneNumber,
                    sectorLabelText = "PHONE NUMBER",
                    placeholderText = "Your phone number...",
                    enabled = !isEditable,
                    onValueChanged = {
                        onPhoneChanged(it)
                        phoneNumberSubmitCondition = true
                    }
                )
            }

            EditTextSector(
                modifier = Modifier.fillMaxWidth(),
                isError = schoolName.filterNot { it.isLetter() }.count() > 0,
                isNotValid = !schoolNameSubmitCondition,
                errorText = "*Only accepts letter",
                invalidText = "At least 3 characters",
                value = schoolName,
                sectorLabelText = "UNIVERSITY NAME",
                placeholderText = "Your university name...",
                enabled = !isEditable,
                onValueChanged = {
                    onSchoolNameChanged(it)
                    schoolNameSubmitCondition = true
                }
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
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.background
                        ),
                        shape = ShapeDefaults.Medium,
                        onClick = {
                            submit(
                                name = name,
                                phoneNumber = phoneNumber,
                                universityName = schoolName,
                                onNameConditionChanged = {
                                    nameSubmitCondition = it
                                },
                                onPhoneNumberConditionChanged = {
                                    phoneNumberSubmitCondition = it
                                },
                                onUniversityConditionChanged = {
                                    schoolNameSubmitCondition = it
                                },
                                onSuccessSubmit = {
                                    showDialog = true
                                    onSubmitClick()
                                }
                            )
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 32.dp),
                            text = "Submit",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
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

private fun submit(
    name: String,
    phoneNumber: String,
    universityName: String,
    onNameConditionChanged: (Boolean) -> Unit,
    onPhoneNumberConditionChanged: (Boolean) -> Unit,
    onUniversityConditionChanged: (Boolean) -> Unit,
    onSuccessSubmit: () -> Unit
) {
    onNameConditionChanged(true)
    onPhoneNumberConditionChanged(true)
    onUniversityConditionChanged(true)

    if (!Checker.checkProfileUsername(name)) {
        onNameConditionChanged(false)
    }

    if (!Checker.checkProfileUniversityName(universityName)) {
        onPhoneNumberConditionChanged(false)
    }

    if (!Checker.checkProfilePhoneNumber(phoneNumber)) {
        onUniversityConditionChanged(false)
    }

    if (
        Checker.checkProfileUsername(name)
        && Checker.checkProfileUniversityName(universityName)
        && Checker.checkProfilePhoneNumber(phoneNumber)
    ) {
        onSuccessSubmit()
    }
}

