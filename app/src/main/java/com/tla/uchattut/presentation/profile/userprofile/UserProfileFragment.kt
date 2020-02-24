package com.tla.uchattut.presentation.profile.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tla.uchattut.R
import com.tla.uchattut.data.repositories.profile.models.ProfileRepoModel
import com.tla.uchattut.presentation._common.BaseFragment
import com.tla.uchattut.presentation._common.viewModel
import kotlinx.android.synthetic.main.fragment_user_profile.*
import javax.inject.Inject

class UserProfileFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModel<UserProfileViewModel>(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profileLiveData.observe(viewLifecycleOwner, Observer {
            updateProfile(it)
        })

        viewModel.requestProfile()
    }

    private fun updateProfile(profile: ProfileRepoModel) {
        usernameTextView.text = profile.name
        phoneNumberTextView.text = profile.phone
        addressTextView.text = profile.address

        Glide.with(this)
            .load(profile.photoUrl)
            .into(avatarImageView)
    }
}