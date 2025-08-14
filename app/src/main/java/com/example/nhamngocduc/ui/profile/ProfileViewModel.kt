package com.example.nhamngocduc.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhamngocduc.domain.manager.SessionManager
import com.example.nhamngocduc.domain.model.User
import com.example.nhamngocduc.domain.usecases.user.UserUseCases
import com.example.nhamngocduc.util.Checker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userUseCases: UserUseCases,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileContract.State())
    val uiState: StateFlow<ProfileContract.State> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ProfileContract.Event>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val scope = viewModelScope

    val username = sessionManager.currentUsername
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ""
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val userProfileFlow: Flow<User?> = username.flatMapLatest {
        username -> userUseCases.getUser(username)
    }

    init {
        scope.launch {
            userProfileFlow.collect { user ->
                user?.let {
                    _uiState.update { it.copy(
                        name = user.name,
                        phoneNumber = user.phone,
                        universityName = user.university,
                        description = user.description,
                        profilePicture = user.profileImage
                    ) }
                }
            }
        }
    }

    fun processIntent(intent: ProfileContract.Intent) {
        when (intent) {
            is ProfileContract.Intent.ToggleEditableMode -> _uiState.update { it.copy(isEditable = !_uiState.value.isEditable) }

            is ProfileContract.Intent.ChangeInput -> changeInput(intent.inputType, intent.input)

            is ProfileContract.Intent.ChangeProfilePicture -> _uiState.update { it.copy(profilePicture = intent.uri) }

            is ProfileContract.Intent.SubmitProfile -> {
                validateAndSubmit()
            }
            is ProfileContract.Intent.HideDialog -> {
                _uiState.update {it.copy(showDialog = false)}
            }
        }
    }

    /**
     * Init the data for viewmodel
     */
    private fun loadUserProfile() {
        scope.launch {
            userUseCases.getUser(username.value!!).collect { user ->
                user?.let {
                    _uiState.update { it.copy(
                        name = user.name,
                        phoneNumber = user.phone,
                        universityName = user.university,
                        description = user.description,
                        profilePicture = user.profileImage
                    ) }
                }
            }
        }
    }

    private fun changeInput(inputType: InputType, input: String) {
        when(inputType) {
            InputType.NAME -> _uiState.update { it.copy(
                name = input,
                inputValidation = _uiState.value.inputValidation.copy(nameSubmitCondition = "")
            ) }

            InputType.UNIVERSITY -> _uiState.update { it.copy(
                universityName = input,
                inputValidation = _uiState.value.inputValidation.copy(schoolNameSubmitCondition = "")
            ) }

            InputType.PHONE -> _uiState.update { it.copy(
                phoneNumber = input,
                inputValidation = _uiState.value.inputValidation.copy(phoneNumberSubmitCondition = "")
            ) }

            InputType.DESCRIPTION -> _uiState.update { it.copy(
                description = input
            ) }
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
            updateProfile(currentState)
            _uiState.update { it.copy(isEditable = false, showDialog = true) }
            finishEditMode()
        }
    }

    /**
     * Update profile to the database
     */
    private fun updateProfile(state: ProfileContract.State) {
        scope.launch {
            userUseCases.updateProfileAtomically(
                username.value!!,
                state.name,
                state.phoneNumber,
                state.universityName,
                state.description,
                state.profilePicture
            )
        }
    }

    private fun finishEditMode() {
        _uiState.update {
            it.copy(
                isEditable = false,
                showDialog = true
            )
        }
    }
}