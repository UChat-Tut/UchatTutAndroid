package com.tla.uchattut.presentation.auth.view_model

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.tla.uchattut.R
import com.tla.uchattut.presentation.Screen
import com.tla.uchattut.presentation.resources.ResourceManager


class AuthViewModel(
    private val resourceManager: ResourceManager
) : ViewModel() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    val screenLiveData = MutableLiveData<Screen>()
    val toastLiveData = MutableLiveData<String>()

    fun createUserWithEmailAndPassword(activity: Activity, email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    screenLiveData.postValue(Screen.MAIN)
                } else {
                    val message = resourceManager.getString(R.string.fail_auth)
                    toastLiveData.postValue(message)
                }
            }

    fun signIn(activity: Activity, email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val message = resourceManager.getString(R.string.success_sign_in)
                    toastLiveData.postValue(message)
                    screenLiveData.postValue(Screen.MAIN)
                } else {
                    val message = resourceManager.getString(R.string.success_sign_in)
                    toastLiveData.postValue(message)
                }
            }
}