package com.tla.uchattut.presentation.auth.view_model

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.tla.uchattut.R
import com.tla.uchattut.presentation._common.resources.ResourceManager


class AuthViewModel(
    private val resourceManager: ResourceManager
) : ViewModel() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    val isAuthenticatedLiveData = MutableLiveData<Boolean>()
    val toastLiveData = MutableLiveData<String>()

    fun createUserWithEmailAndPassword(activity: Activity, email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    isAuthenticatedLiveData.postValue(true)
                } else {
                    val message = resourceManager.getString(R.string.fail_auth)
                    toastLiveData.postValue(message)
                }
            }

    fun signIn(activity: Activity, email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.w("auth", "Success")
                    val message = resourceManager.getString(R.string.success_sign_in)
                    toastLiveData.postValue(message)
                    isAuthenticatedLiveData.postValue(true)
                } else {
                    Log.w("auth", "Failed")
                    val message = resourceManager.getString(R.string.success_sign_in)
                    toastLiveData.postValue(message)
                }
            }
}