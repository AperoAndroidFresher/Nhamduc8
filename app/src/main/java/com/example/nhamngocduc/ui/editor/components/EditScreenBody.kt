package com.example.nhamngocduc.ui.editor.components

import android.net.Uri
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
import com.example.nhamngocduc.ui.components.ScaledTextButton
import com.example.nhamngocduc.ui.editor.ProfileContract
import com.example.nhamngocduc.util.Checker
import kotlinx.coroutines.delay

@Composable
fun EditScreenBody(
    modifier: Modifier = Modifier,
    state: ProfileContract.State,
    onSubmitClick: () -> Unit,
    onNameChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onSchoolNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onProfilePictureChanged: (Uri) -> Unit,
    onHideDialog: () -> Unit
) {
    val name = state.name

    val phoneNumber = state.phoneNumber

    val universityName = state.universityName

    val description = state.description

    val isEditable = state.isEditable

    val profilePicture = state.profilePicture

    val showDialog = state.showDialog

    val nameSubmitCondition = state.inputValidation.nameSubmitCondition

    val phoneNumberSubmitCondition = state.inputValidation.phoneNumberSubmitCondition

    val schoolNameSubmitCondition = state.inputValidation.schoolNameSubmitCondition


    var showDialogContent by rememberSaveable {
        mutableStateOf(false)
    }



    LaunchedEffect(showDialog) {
        showDialogContent = true
        delay(1500L)
        showDialogContent = false
        delay(500L)
        onHideDialog()
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
                profilePicture = profilePicture,
                size = 128.dp,
                borderColor = MaterialTheme.colorScheme.primary,
                changeProfilePicture = onProfilePictureChanged
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                EditTextSector(
                    modifier = Modifier.weight(1f),
                    isError = Checker.checkOnlyStringError(name),
                    isNotValid = nameSubmitCondition.isNotEmpty(),
                    errorText = "*Only accepts letter",
                    invalidText = nameSubmitCondition,
                    value = name,
                    sectorLabelText = "NAME",
                    placeholderText = "Enter your name...",
                    enabled = !isEditable,
                    onValueChanged = onNameChanged
                )

                EditTextSector(
                    modifier = Modifier.weight(1f),
                    isError = Checker.checkOnlyNumberError(phoneNumber),
                    isNotValid = phoneNumberSubmitCondition.isNotEmpty(),
                    keyboardType = KeyboardType.Number,
                    errorText = "*Only accepts number",
                    invalidText = phoneNumberSubmitCondition,
                    value = phoneNumber,
                    sectorLabelText = "PHONE NUMBER",
                    placeholderText = "Your phone number...",
                    enabled = !isEditable,
                    onValueChanged = onPhoneChanged
                )
            }

            EditTextSector(
                modifier = Modifier.fillMaxWidth(),
                isError = Checker.checkOnlyStringError(universityName),
                isNotValid = schoolNameSubmitCondition.isNotEmpty(),
                errorText = "*Only accepts letter",
                invalidText = schoolNameSubmitCondition,
                value = universityName,
                sectorLabelText = "UNIVERSITY NAME",
                placeholderText = "Your university name...",
                enabled = !isEditable,
                onValueChanged = onSchoolNameChanged
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
                    onClick = onSubmitClick
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
