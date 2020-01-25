package com.tla.uchattut.data.repositories.auth

import android.content.Context
import androidx.core.content.edit
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.tla.uchattut.App
import com.tla.uchattut.data.network.RestAuthApi
import com.tla.uchattut.data.network.ResultWrapper
import com.tla.uchattut.data.network.model.auth.RegisterNetworkModel
import com.tla.uchattut.data.network.safeApiCall
import com.tla.uchattut.di.auth.AuthScope
import javax.inject.Inject

@AuthScope
class AuthRepository @Inject constructor(
    private val networkApi: RestAuthApi
) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val user = firebaseAuth.currentUser

    private val authPreserences =
        App.context.getSharedPreferences(AUTH_PREF_NAME, Context.MODE_PRIVATE)

    fun isAuthenticatedUser(): Boolean {
        return user != null && getAuthToken() != null && getAuthToken()!!.isNotBlank()
    }

    fun sendEmailVerification(): Task<Void>? =
        user?.sendEmailVerification()

    fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
        firebaseAuth.signInWithEmailAndPassword(email, password)

    fun isEmailVerified(): Boolean =
        firebaseAuth.currentUser?.isEmailVerified ?: false

    fun getAuthToken(): String? {
        return authPreserences.getString(TOKEN_PREF_KEY, null)
    }

    fun saveAuthToken(token: String) {
        authPreserences.edit {
            putString(TOKEN_PREF_KEY, token)
        }
    }

    suspend fun createUserWithEmailAndPasswordAtServer(
        name: String,
        email: String,
        password: String
    ): ResultWrapper<RegisterNetworkModel.Response> {
        val registerRequest = RegisterNetworkModel.Request(email, password, name, "Surnmae")
        return safeApiCall { networkApi.register(registerRequest) }
    }

    companion object {
        const val AUTH_PREF_NAME = "auth_pref"

        const val TOKEN_PREF_KEY = "token"
    }
}