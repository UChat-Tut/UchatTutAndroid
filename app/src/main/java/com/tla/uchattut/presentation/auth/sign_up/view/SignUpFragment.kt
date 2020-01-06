package com.tla.uchattut.presentation.auth.sign_up.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tla.uchattut.R
import com.tla.uchattut.presentation._common.resources.AndroidResourceManager
import com.tla.uchattut.presentation._common.toast
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.auth.sign_up.view_model.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by lazy {
        viewModel { SignUpViewModel(AndroidResourceManager(context!!)) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInButton.setOnClickListener {
            findNavController().navigate(R.id.action_to_signInFragment)
        }

        signUpButton.setOnClickListener {
            val fullName = nameTextView.text?.toString()
            val email = emailTextView.text?.toString()
            val password = passwordTextView.text?.toString()
            val passwordConfirmation = confirmPasswordTextView.text?.toString()

            viewModel.signUp(activity!!, fullName, email, password, passwordConfirmation)
        }

        viewModel.navigateToMainScreenLiveEvent.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.navigation_main)
        })

        viewModel.toastLiveEvent.observe(viewLifecycleOwner, Observer { message ->
            toast(message!!)
        })

        viewModel.passwordTextViewErrorLiveData.observe(viewLifecycleOwner, Observer {
            passwordTextView.error = it
        })

        viewModel.confirmPassTextViewErrorLiveData.observe(viewLifecycleOwner, Observer {
            confirmPasswordTextView.error = it
        })
    }
}