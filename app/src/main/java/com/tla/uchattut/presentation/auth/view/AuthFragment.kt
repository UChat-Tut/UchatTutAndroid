package com.tla.uchattut.presentation.auth.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tla.uchattut.R
import com.tla.uchattut.presentation.Screen
import com.tla.uchattut.presentation.auth.view_model.AuthViewModel
import com.tla.uchattut.presentation.resources.AndroidResourceManager
import com.tla.uchattut.presentation.viewModel
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_auth.view.*

class AuthFragment : Fragment(), View.OnClickListener {

    private lateinit var authViewModel: AuthViewModel

    private lateinit var root: View

    private lateinit var buttonSignIn:Button
    private lateinit var buttonSignUp:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity!!.applicationContext
        authViewModel = viewModel {AuthViewModel(AndroidResourceManager(context))}
        root = inflater.inflate(R.layout.activity_auth,container,false)

        root.button_sign_in.setOnClickListener(this)
        root.button_sign_up.setOnClickListener(this)

        authViewModel.screenLiveData.observe(this, Observer { screen ->
            if (screen == Screen.MAIN) {
                navigateToMain()
            }
        })

        authViewModel.toastLiveData.observe(this, Observer { message ->
            makeText(message)
        })

        return root
    }

    override fun onClick(v: View?) {
        Log.w("auth", "Click")
        val email = tv_email_input.text?.toString()
        val password = tv_pass_input.text?.toString()

        if(email.isNullOrEmpty()||password.isNullOrEmpty()) {
            makeText("Не все поля введены")//Make is string resource later
            return
        }

            when(v) {
                root.button_sign_up -> authViewModel.createUserWithEmailAndPassword(
                    activity as FragmentActivity, email, password)
                root.button_sign_in -> authViewModel.signIn(
                    activity as FragmentActivity, email, password)


        }
    }

    private fun navigateToMain() {
        findNavController().navigate(R.id.navigation_main)
    }

    private fun makeText(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}