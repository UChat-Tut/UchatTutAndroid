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
                        toastLiveData.postValue("Email не подтвержден")
                        visibilityLiveData.postValue(View.GONE)}
                } else {
                    val message = "Sign In Failed"
                    toastLiveData.postValue(message)
                    visibilityLiveData.postValue(View.GONE)
                }
            }
    }



    private fun sendEmail(){
        auth.currentUser?.sendEmailVerification()?.addOnCompleteListener{task->
            if(task.isSuccessful) {
                visibilityLiveData.postValue(View.GONE)
                toastLiveData.postValue("Вам отправлено письмо на электронную почту")
            } else {
                toastLiveData.postValue("Ошибка при отправке сообщения с подтверждением")
                visibilityLiveData.postValue(View.GONE)
            }

        }
    }

}