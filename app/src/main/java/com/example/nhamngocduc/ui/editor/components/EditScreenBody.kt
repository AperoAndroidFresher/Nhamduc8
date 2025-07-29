package com.example.nhamngocduc.ui.editor.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.nhamngocduc.ui.components.ScaledTextButton
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileImage(
                modifier = Modifier
                    .fillMaxWidth(),
                editable = isEditable,
                size = 128.dp,
                borderColor = MaterialTheme.colorScheme.primary
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                EditTextSector(
                    modifier = Modifier.weight(1f),
                    isError = Checker.checkOnlyStringError(name),
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
                    isError = Checker.checkOnlyNumberError(phoneNumber),
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
                isError = Checker.checkOnlyStringError(schoolName),
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
                minLines = 6,
                maxLines = 6,
                sectorLabelText = "DESCRIBE YOURSELF",
                placeholderText = "Enter a description about yourself...",
                enabled = !isEditable,
                onValueChanged = onDescriptionChanged
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )

            AnimatedVisibility(
                visible = isEditable,
                enter = slideInVertically{ it -> it },
                exit = scaleOut(tween(500)),
            ) {
                ScaledTextButton(
                    modifier = Modifier.padding(horizontal = 128.dp).fillMaxWidth(),
                    label = "Submit",
                    shape = RoundedCornerShape(percent = 25),
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
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

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
        onUniversityConditionChanged(false)
    }

    if (!Checker.checkProfilePhoneNumber(phoneNumber)) {
        onPhoneNumberConditionChanged(false)
    }

    if (
        Checker.checkProfileUsername(name)
        && Checker.checkProfileUniversityName(universityName)
        && Checker.checkProfilePhoneNumber(phoneNumber)
    ) {
        onSuccessSubmit()
    }
}
