package com.tla.uchattut.presentation.profile.userprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tla.uchattut.data.repositories.profile.FakeProfileRepository
import com.tla.uchattut.data.repositories.profile.models.ProfileRepoModel
import com.tla.uchattut.domain.profile.ProfileInteractor
import com.tla.uchattut.presentation._common.ScopeViewModel
import javax.inject.Inject

class UserProfileViewModel : ViewModel() {
    private val profileInteractor = ProfileInteractor(FakeProfileRepository())
    val profileLiveData = MutableLiveData<ProfileRepoModel>()

    fun requestProfile() {
        val profile = profileInteractor.getProfile()
        profileLiveData.postValue(profile)
    }
}