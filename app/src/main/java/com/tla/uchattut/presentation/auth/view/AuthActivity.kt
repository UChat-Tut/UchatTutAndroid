package com.tla.uchattut.presentation.auth.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tla.uchattut.App
import com.tla.uchattut.R
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation._common.openActivity
import com.tla.uchattut.presentation._common.popEntireBackStack
import com.tla.uchattut.presentation.auth.view_model.AuthViewModel
import com.tla.uchattut.presentation.main.MainActivity
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerContainer.authComponent(this, this)
            .inject(this)

        setContentView(R.layout.activity_auth)

        viewModel.navigateToMainScreenLiveEvent.observe(this, Observer {
            App.context.openActivity(MainActivity::class.java)
        })

        replaceScreen(SignUpFragment())
    }

    fun replaceScreen(fragment: Fragment, addToBackStack: Boolean = false) {
        supportFragmentManager.popEntireBackStack()
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.authFragmentContainer, fragment)
        if(addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}