package com.tla.uchattut.data.repositories.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.tla.uchattut.di.auth.AuthScope
import javax.inject.Inject

@AuthScope
class AuthRepository @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val user = auth.currentUser

    fun isAuthenticatedUser(): Boolean {
        return user != null
    }

    fun getCurrentUserId(): String? {
        return user?.uid
    }

    fun sendEmailVerification(): Task<Void>? =
        user?.sendEmailVerification()

    fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
        auth.createUserWithEmailAndPassword(email, password)

    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
        auth.signInWithEmailAndPassword(email, password)

    fun isEmailVerified(): Boolean =
        auth.currentUser?.isEmailVerified ?: false
}