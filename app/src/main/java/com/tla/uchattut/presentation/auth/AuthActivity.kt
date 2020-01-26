package com.tla.uchattut.presentation.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tla.uchattut.App
import com.tla.uchattut.R
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation._common.openActivity
import com.tla.uchattut.presentation._common.popEntireBackStack
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.main.MainActivity
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModel<AuthViewModel>(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerContainer.authComponent(this)
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