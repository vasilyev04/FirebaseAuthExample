package com.vasilyev.firebaseauth.di.modules

import com.vasilyev.firebaseauth.data.firebase.Auth
import com.vasilyev.firebaseauth.data.repository.AuthRepositoryImpl
import com.vasilyev.firebaseauth.di.scopes.ApplicationScope
import com.vasilyev.firebaseauth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    companion object{
        @ApplicationScope
        @Provides
        fun provideFirebaseAuth() = Auth
    }
}