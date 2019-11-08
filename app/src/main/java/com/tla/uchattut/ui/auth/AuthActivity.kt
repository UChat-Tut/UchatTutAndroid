package com.tla.uchattut.ui.auth

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.tla.uchattut.R
import com.tla.uchattut.ui.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var auth: FirebaseAuth


    //TODO("По хорошему здесь вообще всё надо поменять. Я решил, что это будет фрагмент, а не Activity
    // И экран регистрации соответственно тоже фрагмент. Для того, чтобы кнопкой назад нельзя было перейти в основной функционал,
    // сделаю фрагмент домашним или переопределю onBackPressed() так, чтобы он ничего не делал")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        auth = FirebaseAuth.getInstance()


        //TODO("Сделать проверку на null")
        //При попытке нажать кнопку когда не введено ничего вылетает NPE
        button_sign_in.setOnClickListener{
            val email = tv_email_input.text.toString()
            val password = tv_pass_input.text.toString()

            signIn(email,password)
        }

        //Ну и разумеется надо сделать отдельный экран для авторизациии и подтверждения почты.
        //Пока не знаю, будет это фрагмент или активити
        button_sign_up.setOnClickListener{
            val email = tv_email_input.text.toString()
            val password = tv_pass_input.text.toString()
            createAccount(email,password)
        }



    }

    //Это не очень хороший код, но я пока плохо шарю в многопоточности. Поэтому
    //это будет пока здесь и выполняться на мейн треде. Потом сделаю нормально. Или не сделаю. Посмотрим

    private fun createAccount(email: String, password: String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful) {
                    goToMain()
                } else {
                    Toast.makeText(baseContext,"Auth Failed",Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    //Работает медленно
    //TODO("Добавить индикатор загрузки")
    private fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful) {
                    Toast.makeText(baseContext,"Sign In Success",Toast.LENGTH_SHORT)
                        .show()
                    goToMain()
                } else {
                    Toast.makeText(baseContext,"Sign In Failed",Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }


    private fun goToMain() {
       val intent = Intent(applicationContext, MainActivity::class.java)
           .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//TODO("Костыль, убрать при первой возможности")
       applicationContext.startActivity(intent)

    }
}