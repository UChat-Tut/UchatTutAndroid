package com.tla.uchattut.presentation.auth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_auth.*
import kotlinx.android.synthetic.main.fragment_auth.view.*

class AuthFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authViewModel = viewModel {AuthViewModel(AndroidResourceManager(context!!))}
        root = inflater.inflate(R.layout.fragment_auth,container,false)

        root.button_sign_in.setOnClickListener{
            navigateToSignIn()
        }
        root.button_sign_up.setOnClickListener{
            val email = tv_email_input.text?.toString()
            val password = tv_pass_input.text?.toString()
            if(email.isNullOrEmpty()||password.isNullOrEmpty()) {
                makeText("Не все поля введены")//Make it string resource later
            } else authViewModel.createUserWithEmailAndPassword(
                activity as FragmentActivity, email, password
            )
        }

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


    private fun navigateToMain() {
        findNavController().navigate(R.id.navigation_main)
    }

    private fun navigateToSignIn(){
        findNavController().navigate(R.id.navigation_sign_in)
    }

    private fun makeText(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}