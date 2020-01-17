package com.tla.uchattut.presentation.auth.sign_in.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tla.uchattut.App
import com.tla.uchattut.R
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation._common.toast
import com.tla.uchattut.presentation.auth.sign_in.view_model.SignInViewModel
import kotlinx.android.synthetic.main.fragment_sign_in.*
import javax.inject.Inject

class SignInFragment : Fragment() {

    @Inject
    lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerContainer.authComponent(App.context, this)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInButton.setOnClickListener {
            val email = emailTextView.text?.toString()
            val password = passwordTextView.text?.toString()
            viewModel.signIn(activity!!, email, password)
        }

        viewModel.navigateToMainScreenLiveEvent.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.navigation_main)
        })

        viewModel.toastLiveEvent.observe(viewLifecycleOwner, Observer { message ->
            toast(message!!)
        })

        viewModel.emailTextViewErrorLiveData.observe(viewLifecycleOwner, Observer {
            emailTextView.error = it
        })

        viewModel.passwordTextViewErrorLiveData.observe(viewLifecycleOwner, Observer {
            passwordTextView.error = it
        })
    }
}