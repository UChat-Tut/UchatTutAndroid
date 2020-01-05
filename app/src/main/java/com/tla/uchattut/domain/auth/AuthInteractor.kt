package com.tla.uchattut.domain.auth

import com.tla.uchattut.data.repositories.auth.AuthRepository

class AuthInteractor(
    private val authRepository: AuthRepository
) {

    fun isAuthenticatedUser(): Boolean =
        authRepository.isAuthenticatedUser()
}