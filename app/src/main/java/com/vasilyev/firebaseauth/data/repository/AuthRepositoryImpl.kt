package com.vasilyev.firebaseauth.data.repository

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.vasilyev.firebaseauth.data.firebase.Auth
import com.vasilyev.firebaseauth.di.scopes.ApplicationScope
import com.vasilyev.firebaseauth.domain.repository.AuthRepository
import com.vasilyev.firebaseauth.domain.repository.AuthResult
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth
): AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult {
        return try {
            auth.login(email, password)

            AuthResult.Success
        }catch (e: FirebaseAuthInvalidCredentialsException ){
            AuthResult.Failure(AuthResult.Error.INCORRECT_EMAIL_OR_PASSWORD)
        }catch (e: Exception){
            AuthResult.Failure(AuthResult.Error.UNDEFINED_ERROR)
        }
    }

    override suspend fun register(email: String, password: String): AuthResult {
        return try {
            auth.register(email, password)

            AuthResult.Success
        }catch (e: FirebaseAuthUserCollisionException){
            AuthResult.Failure(AuthResult.Error.USER_ALREADY_EXISTS)
        }catch (e: FirebaseAuthInvalidCredentialsException){
            AuthResult.Failure(AuthResult.Error.INVALID_EMAIL)
        }catch (e: Exception){
            AuthResult.Failure(AuthResult.Error.UNDEFINED_ERROR)
        }
    }
}
