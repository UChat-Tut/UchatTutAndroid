package com.tla.uchattut.presentation.auth.sign_up.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tla.uchattut.App
import com.tla.uchattut.R
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation._common.toast
import com.tla.uchattut.presentation.auth.sign_in.view.SignInFragment
import com.tla.uchattut.presentation.auth.sign_up.view_model.SignUpViewModel
import com.tla.uchattut.presentation.main.MainActivity
import com.tla.uchattut.presentation.main.MainFragment
import kotlinx.android.synthetic.main.fragment_sign_up.*
import javax.inject.Inject

class SignUpFragment : Fragment() {

    @Inject
    lateinit var viewModel: SignUpViewModel

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
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInButton.setOnClickListener {
            val mainActivity = activity as? MainActivity
            mainActivity?.replaceScreen(SignInFragment())
        }

        signUpButton.setOnClickListener {
            val fullName = nameTextView.text?.toString()
            val email = emailTextView.text?.toString()
            val password = passwordTextView.text?.toString()
            val passwordConfirmation = confirmPasswordTextView.text?.toString()

            viewModel.signUp(activity!!, fullName, email, password, passwordConfirmation)
        }

        viewModel.navigateToMainScreenLiveEvent.observe(viewLifecycleOwner, Observer {
            val mainActivity = activity as? MainActivity
            mainActivity?.replaceScreen(MainFragment())
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

    companion object {
        const val TAG = "SignUpFragment"
    }
}