package com.vasilyev.firebaseauth.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasilyev.firebaseauth.domain.repository.AuthResult
import com.vasilyev.firebaseauth.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel(){

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState>
        get() = _loginState

    fun login(email: String, password: String){
        if(!ensureFieldsNotEmpty(email, password)){
            _loginState.value = LoginState.EmptyFieldsError
            return
        }

        viewModelScope.launch {
            val result = loginUseCase(email, password)

            when(result){
                is AuthResult.Success -> {
                    _loginState.value = LoginState.Success
                }

                is AuthResult.Failure -> {
                    _loginState.value = when(result.error){

                        AuthResult.Error.INCORRECT_EMAIL_OR_PASSWORD ->
                          LoginState.IncorrectEmailOrPasswordError

                        AuthResult.Error.UNDEFINED_ERROR ->
                           LoginState.UndefinedError

                        else -> null
                    }
                }
            }
        }
    }

    private fun ensureFieldsNotEmpty(email: String, password: String)
        = email.isNotBlank() && password.isNotBlank()
}