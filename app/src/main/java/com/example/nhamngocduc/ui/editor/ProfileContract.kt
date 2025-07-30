package com.example.nhamngocduc.ui.editor

import android.net.Uri

object ProfileContract {
    data class State(
        val isEditable: Boolean = false,
        val profilePicture: Uri? = null,
        val name: String = "",
        val phoneNumber: String = "",
        val universityName: String = "",
        val description: String = "",
        val inputValidation: InputState = InputState(),
        val showDialog: Boolean = false
    )

    data class InputState(
        val nameSubmitCondition: String = "",
        val phoneNumberSubmitCondition: String = "",
        val schoolNameSubmitCondition: String = ""
    )

    sealed interface Intent {
        data class ChangeInput(val input: String, val inputType: InputType) : Intent
        data class ChangeProfilePicture(val uri: Uri) : Intent
        data object ToggleEditableMode : Intent
        data object SubmitProfile : Intent
        data class SetValidationCondition(val inputType: InputType, val condition: String) : Intent
        data object HideDialog: Intent
    }

    sealed interface Event {
        data object ChangeTheme: Event
    }
}

enum class InputType {
    NAME,
    UNIVERSITY,
    PHONE,
    DESCRIPTION
}