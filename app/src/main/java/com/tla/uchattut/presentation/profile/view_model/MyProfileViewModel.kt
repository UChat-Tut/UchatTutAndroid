package com.tla.uchattut.presentation.profile.view_model

import androidx.lifecycle.MutableLiveData
import com.tla.uchattut.data.repositories.profile.models.ProfileRepoModel
import com.tla.uchattut.di.myprofile.MyProfileComponent
import com.tla.uchattut.domain.profile.ProfileInteractor
import com.tla.uchattut.presentation._common.ScopeViewModel
import javax.inject.Inject

class MyProfileViewModel @Inject constructor(
    private val profileInteractor: ProfileInteractor
) : ScopeViewModel(MyProfileComponent::class) {

    val profileLiveData = MutableLiveData<ProfileRepoModel>()

    fun requestProfile() {
        val profile = profileInteractor.getProfile()
        profileLiveData.postValue(profile)
    }
}