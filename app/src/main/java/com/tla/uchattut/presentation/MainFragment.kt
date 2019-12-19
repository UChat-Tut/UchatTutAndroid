package com.tla.uchattut.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tla.uchattut.R

class MainFragment: Fragment() {
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity?.applicationContext
        val root = inflater.inflate(R.layout.fragment_main,container,false)
        val toolbar = root.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        navController = findNavController(root.findViewById(R.id.bottom_nav_host_fragment))

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_chat,
                R.id.navigation_dashboard,
                R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(
            activity as AppCompatActivity,
            navController,
            appBarConfiguration)

        val bottomNavBar = root.findViewById<BottomNavigationView>(R.id.nav_view)

        bottomNavBar.setupWithNavController(navController)


        return root
    }




}