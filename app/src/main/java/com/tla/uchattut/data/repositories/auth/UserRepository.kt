package com.tla.uchattut.data.repositories.auth

import com.google.firebase.auth.FirebaseAuth

class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val user = auth.currentUser

    fun isAuthenticatedUser(): Boolean {
        return user != null
    }

    fun getCurrentUserId(): String? {
        return user?.uid
    }
}