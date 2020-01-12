package com.tla.uchattut.presentation.auth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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


class AuthFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel
    private var progressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authViewModel = viewModel { AuthViewModel(AndroidResourceManager(context!!)) }

        authViewModel.screenLiveData.observe(this, Observer { screen ->
            if (screen == Screen.MAIN) {
                navigateToMain()
            }
        })


        authViewModel.toastLiveData.observe(this, Observer { message ->
            makeText(message)
        })

        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = activity?.findViewById(R.id.progressBar)

        authViewModel.visibilityLiveData.observe(this, Observer { visibility ->
            progressBar?.visibility = visibility
        })

        button_sign_in.setOnClickListener {
            navigateToSignIn()
        }

        button_sign_up.setOnClickListener {//Can be later added regex email confirmation
            val fullName = tv_name_input.text?.toString()
            val email = tv_email_input.text?.toString()
            val password = tv_pass_input.text?.toString()
            val passwordConfirmation = tv_confirm_password.text?.toString()
            when {
                areFieldsBlank(fullName, email, password, passwordConfirmation) -> {
                    val message = resources.getString(R.string.not_all_fields_are_entered)
                    makeText(message)
                }
                password!!.length < 6 ->
                    tv_pass_input.error = resources.getString(R.string.incorrect_pass_input)
                password != passwordConfirmation ->
                    tv_confirm_password.error = resources.getString(R.string.passwords_dont_match)
                else -> authViewModel.createUserWithEmailAndPassword(
                    activity as FragmentActivity, email!!, password
                )
            }
        }

    }

    private fun navigateToMain() {
        findNavController().navigate(R.id.navigation_main)
    }

    private fun navigateToSignIn() {
        findNavController().navigate(R.id.navigation_sign_in)
    }

    private fun makeText(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun areFieldsBlank(
        fullName: String?,
        email: String?,
        password: String?,
        passwordConfirmation: String?
    )
            : Boolean {
        return email.isNullOrEmpty()
                || password.isNullOrEmpty()
                || passwordConfirmation.isNullOrEmpty()
                || fullName.isNullOrEmpty()

    }
}