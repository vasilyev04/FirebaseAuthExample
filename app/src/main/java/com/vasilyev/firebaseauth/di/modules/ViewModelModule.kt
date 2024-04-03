package com.vasilyev.firebaseauth.di.modules

import androidx.lifecycle.ViewModel
import com.vasilyev.firebaseauth.di.annotations.ViewModelKey
import com.vasilyev.firebaseauth.presentation.login.LoginViewModel
import com.vasilyev.firebaseauth.presentation.register.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @Binds
    @ViewModelKey(RegistrationViewModel::class)
    fun bindRegistrationViewModel(registrationViewModel: RegistrationViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel
}