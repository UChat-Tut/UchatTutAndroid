package com.tla.uchattut.data.repositories.auth

import android.content.Context
import androidx.core.content.edit
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.tla.uchattut.App
import com.tla.uchattut.di.auth.AuthScope
import javax.inject.Inject

class AuthRepository @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val user = auth.currentUser

    private val authPreserences = App.context.getSharedPreferences(AUTH_PREF_NAME, Context.MODE_PRIVATE)

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

    fun getAuthToken(): String? {
        return authPreserences.getString(TOKEN_PREF_KEY, null)
    }

    fun saveAuthToken(token: String) {
        authPreserences.edit {
            putString(TOKEN_PREF_KEY, token)
        }
    }

    companion object {
        private const val AUTH_PREF_NAME = "auth_pref"

        private const val TOKEN_PREF_KEY = "token"
    }
}