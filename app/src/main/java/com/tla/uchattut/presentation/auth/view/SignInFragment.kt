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
import com.tla.uchattut.presentation.auth.view_model.AuthViewModel
import com.tla.uchattut.presentation._common.resources.AndroidResourceManager
import com.tla.uchattut.presentation._common.viewModel
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*

class SignInFragment: Fragment() {

    private val authViewModel by lazy {
        viewModel{ AuthViewModel(AndroidResourceManager(context!!)) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_sign_in.setOnClickListener {
            val email = tv_email_input.text?.toString()
            val password = tv_pass_input.text?.toString()
            when {
                email.isNullOrEmpty()
                        || password.isNullOrEmpty() ->
                    tv_email_input.error = "Не все поля введены"
                password.length < 6 -> tv_pass_input.error = "Возможно, вы неправильно ввели пароль"
                else -> authViewModel.signIn(activity as FragmentActivity,email, password)
            }
        }

        authViewModel.isAuthenticatedLiveData.observe(this, Observer { isAuthenticated ->
            if(isAuthenticated){ navigateToMain() }
        })

        authViewModel.toastLiveData.observe(this, Observer { message ->
            makeText(message)
        })
    }

    private fun navigateToMain(){
        findNavController().navigate(R.id.navigation_main)
    }

    private fun makeText(message: String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }
}