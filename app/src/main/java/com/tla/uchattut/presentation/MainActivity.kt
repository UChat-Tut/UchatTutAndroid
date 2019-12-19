package com.tla.uchattut.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.tla.uchattut.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser

        if (user == null) {
            navController.popBackStack()
            navController.navigate(R.id.navigation_auth)
        }
        progressBar.visibility = View.GONE
    }

}
