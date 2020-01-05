package com.tla.uchattut.domain.auth

import com.tla.uchattut.data.repositories.auth.UserRepository

class AuthInteractor(
    private val authRepository: UserRepository
) {

    fun isAuthenticatedUser(): Boolean =
        authRepository.isAuthenticatedUser()
}