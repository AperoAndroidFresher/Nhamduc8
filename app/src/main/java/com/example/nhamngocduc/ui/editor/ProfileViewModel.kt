package com.example.nhamngocduc.ui.editor

import androidx.lifecycle.ViewModel
import com.example.nhamngocduc.util.Checker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileContract.State())
    val uiState: StateFlow<ProfileContract.State> = _uiState.asStateFlow()

    fun processIntent(intent: ProfileContract.Intent) {
        when (intent) {
            is ProfileContract.Intent.ToggleEditableMode -> {
                _uiState.update { it.copy(isEditable = !_uiState.value.isEditable) }
            }
            is ProfileContract.Intent.ChangeInput -> {
                when(intent.inputType) {
                    InputType.NAME -> _uiState.update { it.copy(name = intent.input) }
                    InputType.UNIVERSITY -> _uiState.update { it.copy(universityName = intent.input) }
                    InputType.PHONE -> _uiState.update { it.copy(phoneNumber = intent.input) }
                    InputType.DESCRIPTION -> _uiState.update { it.copy(description = intent.input) }
                }
            }
            is ProfileContract.Intent.ChangeProfilePicture -> {
                _uiState.update { it.copy(profilePicture = intent.uri) }
            }
            is ProfileContract.Intent.SubmitProfile -> {
                validateAndSubmit()
            }
            is ProfileContract.Intent.SetValidationCondition -> {
                when(intent.inputType) {
                    InputType.NAME -> _uiState.value.copy(
                        inputValidation = _uiState.value.inputValidation.copy(nameSubmitCondition = intent.condition)
                    )
                    InputType.UNIVERSITY -> _uiState.value.copy(
                        inputValidation = _uiState.value.inputValidation.copy(schoolNameSubmitCondition = intent.condition)
                    )
                    InputType.PHONE -> _uiState.value.copy(
                        inputValidation = _uiState.value.inputValidation.copy(phoneNumberSubmitCondition = intent.condition)
                    )
                    else -> _uiState.value
                }
            }
            is ProfileContract.Intent.HideDialog -> {
                _uiState.update {it.copy(showDialog = false)}
            }
        }
    }

    fun processEvent(event: ProfileContract.Event) {
        when(event) {
            is ProfileContract.Event.ChangeTheme -> {
                TODO()
            }
        }
    }

    private fun validateAndSubmit() {
        val currentState = _uiState.value

        var newNameCondition = ""
        var newPhoneNumberCondition = ""
        var newSchoolNameCondition = ""
        var isValid = true

        if (!Checker.checkProfileUsername(currentState.name)) {
            newNameCondition = "At least 3 characters"
            isValid = false
        }
        if (!Checker.checkProfilePhoneNumber(currentState.phoneNumber)) {
            newPhoneNumberCondition = "At least 10 and under 15 digits"
            isValid = false
        }
        if (!Checker.checkProfileUniversityName(currentState.universityName)) {
            newSchoolNameCondition = "At least 3 characters"
            isValid = false
        }

        _uiState.value = _uiState.value.copy(
            inputValidation = ProfileContract.InputState(
                nameSubmitCondition = newNameCondition,
                phoneNumberSubmitCondition = newPhoneNumberCondition,
                schoolNameSubmitCondition = newSchoolNameCondition
            )
        )

        if (isValid) {
            _uiState.update { it.copy(isEditable = false, showDialog = true) }
        }
    }
}