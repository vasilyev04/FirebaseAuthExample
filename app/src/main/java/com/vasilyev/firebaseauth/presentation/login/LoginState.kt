package com.vasilyev.firebaseauth.presentation.login

sealed class LoginState {
    object Success: LoginState()

    object UndefinedError: LoginState()

    object IncorrectEmailOrPasswordError: LoginState()

    object EmptyFieldsError : LoginState()
}