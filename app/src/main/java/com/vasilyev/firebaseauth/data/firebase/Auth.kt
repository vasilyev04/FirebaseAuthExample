package com.vasilyev.firebaseauth.data.firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.vasilyev.firebaseauth.di.scopes.ApplicationScope
import kotlinx.coroutines.tasks.await

@ApplicationScope
object Auth {

    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String): AuthResult {
        return firebaseAuth
            .signInWithEmailAndPassword(email, password)
            .await()
    }

    suspend fun register(email: String, password: String): AuthResult{
        return firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .await()
    }
}