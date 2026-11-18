package com.example.pruebasubicacion.data.datasource

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthDataSource {
    private val auth = FirebaseAuth.getInstance()

    suspend fun login(correo: String, pass: String): AuthResult? {
        return auth.signInWithEmailAndPassword(correo, pass).await()
    }

    suspend fun signUp(correo: String, pass: String): AuthResult? {
        return auth.createUserWithEmailAndPassword(correo, pass).await()
    }

    fun getCurrentUser() = auth.currentUser
    fun logout() = auth.signOut()
}
