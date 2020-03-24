package com.tla.uchattut.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tla.uchattut.App
import com.tla.uchattut.R
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation._common.toast
import com.tla.uchattut.presentation._common.viewModel
import kotlinx.android.synthetic.main.fragment_sign_in.*
import javax.inject.Inject

class SignInFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        activity!!.viewModel<AuthViewModel>(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerContainer.authComponent(App.context)
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