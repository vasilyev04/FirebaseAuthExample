package com.vasilyev.firebaseauth.di.components

import android.app.Application
import com.vasilyev.firebaseauth.di.modules.DataModule
import com.vasilyev.firebaseauth.di.modules.ViewModelModule
import com.vasilyev.firebaseauth.di.scopes.ApplicationScope
import com.vasilyev.firebaseauth.presentation.login.LoginActivity
import com.vasilyev.firebaseauth.presentation.register.RegistrationActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [
    DataModule::class,
    ViewModelModule::class
])
@ApplicationScope
interface ApplicationComponent {

    fun inject(loginActivity: LoginActivity)

    fun inject(registrationActivity: RegistrationActivity)


    @Component.Factory
    interface Factory{

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}