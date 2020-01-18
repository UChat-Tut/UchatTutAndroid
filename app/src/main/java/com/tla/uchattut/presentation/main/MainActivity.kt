package com.tla.uchattut.presentation.main

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.tla.uchattut.App
import com.tla.uchattut.R
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.domain.auth.AuthInteractor
import com.tla.uchattut.presentation._common.BackPressable
import com.tla.uchattut.presentation.auth.sign_up.view.SignUpFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authInteractor: AuthInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerContainer.authComponent(App.context)
            .inject(this)

        openScreen(MainFragment())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }
    }

    override fun onStart() {
        super.onStart()

        if (!authInteractor.isAuthenticatedUser()) {
            openScreen(SignUpFragment())
        }
        progressBar.visibility = View.GONE
    }

    fun openScreen(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainActivityFragmentContainer, fragment)
            .commitNow()
    }

    override fun onBackPressed() {
        val backPressableFragments = supportFragmentManager.fragments
            .filterIsInstance(BackPressable::class.java)

        for (fragment in backPressableFragments) {
            if (fragment.onBackPressed()) {
                return
            }
        }
        super.onBackPressed()
    }
}
