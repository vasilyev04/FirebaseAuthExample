package com.vasilyev.firebaseauth.domain.repository

interface AuthRepository {

    suspend fun login(email: String, password: String): AuthResult

    suspend fun register(email: String, password: String): AuthResult
}

sealed class AuthResult {
    object Success: AuthResult()

    class Failure(val error: Error): AuthResult()

    enum class Error {
        UNMATCHED_PASSWORDS,
        INVALID_EMAIL,
        WEAK_PASSWORD,
        USER_ALREADY_EXISTS,
        INCORRECT_EMAIL_OR_PASSWORD,
        UNDEFINED_ERROR,
    }
}