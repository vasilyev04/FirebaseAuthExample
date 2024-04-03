package com.vasilyev.firebaseauth.domain.usecase

import com.vasilyev.firebaseauth.domain.repository.AuthRepository
import com.vasilyev.firebaseauth.domain.repository.AuthResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String): AuthResult {
        return repository.login(email, password)
    }
}