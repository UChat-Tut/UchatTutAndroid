package com.tla.uchattut.presentation.auth.sign_up.view_model

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tla.uchattut.R
import com.tla.uchattut.data.repositories.auth.AuthRepository
import com.tla.uchattut.domain.auth.AuthInteractor
import com.tla.uchattut.presentation._common.SingleLiveEvent
import com.tla.uchattut.presentation._common.resources.ResourceManager


class SignUpViewModel(
    private val resourceManager: ResourceManager
) : ViewModel() {

    private val authInteractor = AuthInteractor(AuthRepository())

    val navigateToMainScreenLiveEvent = SingleLiveEvent<Void>()
    val toastLiveEvent = SingleLiveEvent<String>()
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
            createUserWithEmailAndPassword(activity, email!!, password)
    }

    private fun createUserWithEmailAndPassword(
        activity: FragmentActivity,
        email: String,
        password: String
    ) {
        authInteractor.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    navigateToMainScreenLiveEvent.call()
                    sendEmail()
                } else {
                    val message = resourceManager.getString(R.string.fail_auth)
                    toastLiveEvent.postValue(message)
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
}