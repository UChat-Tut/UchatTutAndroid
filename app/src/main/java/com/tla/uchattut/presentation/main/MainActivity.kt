package com.tla.uchattut.presentation.main

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.tla.uchattut.R
import com.tla.uchattut.data.repositories.auth.UserRepository
import com.tla.uchattut.domain.auth.AuthInteractor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val authInteractor = AuthInteractor(UserRepository())
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }
    }

    override fun onStart() {
        super.onStart()

        if (!authInteractor.isAuthenticatedUser()) {
            navController.navigate(R.id.navigation_auth)
        }
        progressBar.visibility = View.GONE
    }
}
