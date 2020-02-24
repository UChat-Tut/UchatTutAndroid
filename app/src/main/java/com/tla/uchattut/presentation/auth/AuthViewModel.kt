package com.tla.uchattut.presentation.auth

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tla.uchattut.R
import com.tla.uchattut.data.network.ResultWrapper
import com.tla.uchattut.di.auth.AuthComponent
import com.tla.uchattut.domain.auth.AuthInteractor
import com.tla.uchattut.presentation._common.ScopeViewModel
import com.tla.uchattut.presentation._common.SingleLiveEvent
import com.tla.uchattut.presentation._common.resources.ResourceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authInteractor: AuthInteractor,
    private val resourceManager: ResourceManager
) : ScopeViewModel(AuthComponent::class) {

    val navigateToMainScreenLiveEvent = SingleLiveEvent<Void>()
    val toastLiveEvent = SingleLiveEvent<String>()

    val emailTextViewErrorLiveData = MutableLiveData<String?>(null)
    val passwordTextViewErrorLiveData = MutableLiveData<String?>(null)
    val confirmPassTextViewErrorLiveData = MutableLiveData<String?>(null)

    fun signUp(
        activity: FragmentActivity,
        fullName: String?,
        email: String?,
        password: String?,
        passwordConfirmation: String?
    ) = when {
        authInteractor.hasAnyBlankString(arrayOf(fullName, email, password, passwordConfirmation)) ->
            toastLiveEvent.postValue("Не все поля введены")

        authInteractor.isNotValidPassword(password!!) ->
            passwordTextViewErrorLiveData.postValue("Пароль должен быть больше 6 символов")

        !authInteractor.isPasswordConfirmed(password, passwordConfirmation!!) ->
            confirmPassTextViewErrorLiveData.postValue("Пароли не совпадают")

        else ->
            createUserWithEmailAndPasswordAtFirebase(activity, fullName!!, email!!, password)
    }

    private fun createUserWithEmailAndPasswordAtFirebase(
        activity: FragmentActivity,
        name: String,
        email: String,
        password: String
    ) {
        authInteractor.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    createUserWithEmailAndPasswordAtServer(name, email, password)
                } else {
                    val message = resourceManager.getString(R.string.fail_auth)
                    toastLiveEvent.postValue(message)
                }
            }
    }

    private fun createUserWithEmailAndPasswordAtServer(
        name: String,
        email: String,
        password: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        when (val result = authInteractor.createUserWithEmailAndPasswordAtServer(name, email, password)) {
            is ResultWrapper.Success -> {
                authInteractor.saveAuthToken(result.value.token)
                sendEmail()
                navigateToMainScreenLiveEvent.postCall()
            }
            is ResultWrapper.Error -> {
                println()
            }
        }
    }

    private fun sendEmail() =
        authInteractor.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                toastLiveEvent.postValue("Вам отправлено письмо на электронную почту")
            } else {
                toastLiveEvent.postValue("Ошибка при отправке сообщения с подтверждением")
            }
        }

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
                        navigateToMainScreenLiveEvent.postCall()
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