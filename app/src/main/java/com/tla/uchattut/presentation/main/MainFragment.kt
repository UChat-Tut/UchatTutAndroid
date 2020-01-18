package com.tla.uchattut.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tla.uchattut.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        navController = findNavController(view.findViewById(R.id.bottom_nav_host_fragment))

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_conversation,
                R.id.navigation_library,
                R.id.navigation_schedule,
                R.id.navigation_tasks,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(
            activity as AppCompatActivity,
            navController,
            appBarConfiguration)

        nav_view.setupWithNavController(navController)
    }
}