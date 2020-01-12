package com.tla.uchattut.presentation.auth.view_model

import android.app.Activity
import android.view.View
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
    val visibilityLiveData = MutableLiveData<Int>()

    fun createUserWithEmailAndPassword(activity: Activity, email: String, password: String) {
        visibilityLiveData.postValue(View.VISIBLE)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    sendEmail()
                } else {
                    val message = resourceManager.getString(R.string.fail_auth)
                    toastLiveData.postValue(message)
                    visibilityLiveData.postValue(View.GONE)
                }
            }
    }


    fun signIn(activity: Activity, email: String, password: String) {
        visibilityLiveData.postValue(View.VISIBLE)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val message = resourceManager.getString(R.string.success_sign_in)
                    toastLiveData.postValue(message)
                    if(auth.currentUser!!.isEmailVerified){
                        screenLiveData.postValue(Screen.MAIN)
                        visibilityLiveData.postValue(View.GONE)
                    } else {
                        val message = resourceManager.getString(R.string.email_not_confirmed)
                        toastLiveData.postValue(message)
                        visibilityLiveData.postValue(View.GONE)}
                } else {
                    val message = resourceManager.getString(R.string.fail_sign_in)
                    toastLiveData.postValue(message)
                    visibilityLiveData.postValue(View.GONE)
                }
            }
    }



    private fun sendEmail(){
        auth.currentUser?.sendEmailVerification()?.addOnCompleteListener{task->
            if(task.isSuccessful) {
                val message = resourceManager.getString(R.string.confirmation_email_sent)
                visibilityLiveData.postValue(View.GONE)
                toastLiveData.postValue(message)
            } else {
                val message = resourceManager.getString(R.string.sending_email_error)
                toastLiveData.postValue(message)
                visibilityLiveData.postValue(View.GONE)
            }

        }
    }

}