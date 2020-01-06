package com.tla.uchattut.presentation.auth.sign_in.view_model

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tla.uchattut.R
import com.tla.uchattut.data.repositories.auth.AuthRepository
import com.tla.uchattut.domain.auth.AuthInteractor
import com.tla.uchattut.presentation._common.SingleLiveEvent
import com.tla.uchattut.presentation._common.resources.AndroidResourceManager

class SignInViewModel(
    private val resourceManager: AndroidResourceManager
) : ViewModel() {

    private val authInteractor = AuthInteractor(AuthRepository())

    val toastLiveEvent = SingleLiveEvent<String>()
    val emailTextViewErrorLiveData = MutableLiveData<String?>(null)
    val passwordTextViewErrorLiveData = MutableLiveData<String?>(null)
    val navigateToMainScreenLiveEvent = SingleLiveEvent<Void>()

    fun signIn(activity: FragmentActivity, email: String?, password: String?) =
        when {
            authInteractor.hasAnyBlankString(arrayOf(email, password)) -> {
                toastLiveEvent.postValue("Не все поля введены")
            }
            authInteractor.isNotValidPassword(password!!) -> {
                passwordTextViewErrorLiveData.postValue("Возможно, вы неправильно ввели пароль")
            }
            else -> {
                signInWithEmailAndPassword(activity, email!!, password)
            }
        }

    private fun signInWithEmailAndPassword(activity: Activity, email: String, password: String) {
        authInteractor.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val message = resourceManager.getString(R.string.success_sign_in)
                    toastLiveEvent.postValue(message)
                    if (authInteractor.isEmailVerified()) {
                        navigateToMainScreenLiveEvent.call()
                    } else {
                        toastLiveEvent.postValue("Email не подтвержден")
                    }
                } else {
                    val message = "Sign In Failed"
                    toastLiveEvent.postValue(message)
                }
            }
    }

}