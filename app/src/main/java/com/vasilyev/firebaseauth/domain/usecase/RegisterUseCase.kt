package com.vasilyev.firebaseauth.domain.usecase

import com.vasilyev.firebaseauth.domain.repository.AuthRepository
import com.vasilyev.firebaseauth.domain.repository.AuthResult
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        repeatedPassword: String
    ): AuthResult {
        validatePasswords(password, repeatedPassword)?.let { error ->
            return AuthResult.Failure(error)
        }

        return repository.register(email, password);
    }

    private fun validatePasswords(password: String, repeatedPassword: String): AuthResult.Error? {
        if (password != repeatedPassword) return AuthResult.Error.UNMATCHED_PASSWORDS
        if (password.length < MIN_PASSWORD_LENGTH) return AuthResult.Error.WEAK_PASSWORD
        if (!password.containsLettersAndDigits()) return AuthResult.Error.WEAK_PASSWORD

        return null
    }


    private fun String.containsLettersAndDigits(): Boolean{
        var hasLetters = false
        var hasDigits = false

        for (symbol in this){
            if (symbol.isLetter()) hasLetters = true
            if (symbol.isDigit()) hasDigits = true
        }

        return (hasLetters && hasDigits)
    }

    companion object{
        private const val MIN_PASSWORD_LENGTH = 6
    }
}