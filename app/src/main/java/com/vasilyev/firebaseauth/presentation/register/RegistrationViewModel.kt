package com.vasilyev.firebaseauth.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasilyev.firebaseauth.domain.repository.AuthResult
import com.vasilyev.firebaseauth.domain.usecase.RegisterUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    private val _registerState = MutableLiveData<RegistrationState>()
    val registerState: LiveData<RegistrationState>
        get() = _registerState

    fun register(email: String, password: String, repeatedPassword: String){
        if(!ensureFieldsNotEmpty(email, password, repeatedPassword)){
            _registerState.value = RegistrationState.EmptyFieldsError
            return
        }

        viewModelScope.launch {
            val result = registerUseCase(email, password, repeatedPassword)

            when(result){
                is AuthResult.Success -> {
                    _registerState.value = RegistrationState.Success
                }

                is AuthResult.Failure -> {
                    _registerState.value = when(result.error){
                        AuthResult.Error.UNMATCHED_PASSWORDS ->
                            RegistrationState.UnmatchedPasswordsError

                        AuthResult.Error.WEAK_PASSWORD ->
                            RegistrationState.WeakPasswordError

                        AuthResult.Error.USER_ALREADY_EXISTS ->
                            RegistrationState.UserAlreadyExistsError

                        AuthResult.Error.INVALID_EMAIL ->
                            RegistrationState.InvalidEmailError

                        AuthResult.Error.UNDEFINED_ERROR ->
                            RegistrationState.UndefinedError

                        else -> null
                    }
                }
            }
        }
    }

    private fun ensureFieldsNotEmpty(email: String, password: String, repeatedPassword: String)
            = email.isNotBlank() && password.isNotBlank() && repeatedPassword.isNotBlank()
}