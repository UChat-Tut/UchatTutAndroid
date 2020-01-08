package com.tla.uchattut.domain.profile

import com.tla.uchattut.data.repositories.profile.models.ProfileRepoModel
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    fun getProfile(): ProfileRepoModel =
        profileRepository.getProfile()
}