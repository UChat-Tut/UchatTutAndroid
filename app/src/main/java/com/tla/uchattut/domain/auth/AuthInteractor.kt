package com.tla.uchattut.domain.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.tla.uchattut.data.repositories.auth.AuthRepository

class AuthInteractor(
    private val authRepository: AuthRepository
) {

    fun isAuthenticatedUser(): Boolean =
        authRepository.isAuthenticatedUser()

    fun sendEmailVerification(): Task<Void>? =
        authRepository.sendEmailVerification()

    fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
        authRepository.createUserWithEmailAndPassword(email, password)

    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
        authRepository.signInWithEmailAndPassword(email, password)

    fun isEmailVerified(): Boolean =
        authRepository.isEmailVerified()

    fun isNotValidPassword(password: String): Boolean =
        password.length < MIN_PASSWORD_LENGTH

    fun isPasswordConfirmed(password: String, confirmPassword: String): Boolean =
        password == confirmPassword

    fun hasAnyBlankString(stringsArray: Array<String?>): Boolean {
        stringsArray.forEach {
            if (it.isNullOrBlank()) return true
        }
        return false
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 6
    }
}