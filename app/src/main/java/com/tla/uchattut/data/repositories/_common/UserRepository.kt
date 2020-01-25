package com.tla.uchattut.data.repositories._common

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserRepository @Inject constructor() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val user = firebaseAuth.currentUser

    fun getCurrentUserId(): String? {
        return user?.uid
    }
}