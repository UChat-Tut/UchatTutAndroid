package com.tla.uchattut.presentation.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.tla.uchattut.R
import com.tla.uchattut.data.repositories.profile.models.ProfileRepoModel
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation.profile.view_model.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : Fragment() {

    @Inject
    lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerContainer.profileComponent(this)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
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

    companion object {
        const val TAG = "ProfileFragment"
    }
}