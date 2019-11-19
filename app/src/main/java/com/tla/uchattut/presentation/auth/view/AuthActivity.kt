package com.tla.uchattut.presentation.auth.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tla.uchattut.R
import com.tla.uchattut.presentation.MainActivity
import com.tla.uchattut.presentation.Screen
import com.tla.uchattut.presentation.auth.view_model.AuthViewModel
import com.tla.uchattut.presentation.resources.AndroidResourceManager
import com.tla.uchattut.presentation.viewModel
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    //TODO("По хорошему здесь вообще всё надо поменять. Я решил, что это будет фрагмент, а не Activity
    // И экран регистрации соответственно тоже фрагмент. Для того, чтобы кнопкой назад нельзя было перейти в основной функционал,
    // сделаю фрагмент домашним или переопределю onBackPressed() так, чтобы он ничего не делал")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        authViewModel = viewModel { AuthViewModel(AndroidResourceManager(this)) }

        authViewModel.screenLiveData.observe(this, Observer { screen ->
            if (screen == Screen.MAIN) {
                navigateToMain()
            }
        })

        authViewModel.toastLiveData.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        //TODO("Сделать проверку на null")
        //При попытке нажать кнопку когда не введено ничего вылетает NPE
        button_sign_in.setOnClickListener {
            authViewModel.signIn(
                activity = this,
                email = tv_email_input.text.toString(),
                password = tv_pass_input.text.toString()
            )
        }

        //Ну и разумеется надо сделать отдельный экран для авторизациии и подтверждения почты.
        //Пока не знаю, будет это фрагмент или активити
        button_sign_up.setOnClickListener {
            authViewModel.createUserWithEmailAndPassword(
                activity = this,
                email = tv_email_input.text.toString(),
                password = tv_pass_input.text.toString()
            )
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // TODO("Костыль, убрать при первой возможности")
        startActivity(intent)
    }
}