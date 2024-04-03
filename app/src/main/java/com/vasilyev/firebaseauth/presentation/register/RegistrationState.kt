package com.vasilyev.firebaseauth.presentation.register

sealed class RegistrationState {
    object Success: RegistrationState()

    object UndefinedError: RegistrationState()

    object UnmatchedPasswordsError: RegistrationState()

    object InvalidEmailError: RegistrationState()

    object WeakPasswordError: RegistrationState()

    object UserAlreadyExistsError: RegistrationState()

    object EmptyFieldsError : RegistrationState()
}