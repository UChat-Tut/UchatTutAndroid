package com.tla.uchattut.domain.profile

import com.tla.uchattut.data.repositories.profile.models.ProfileRepoModel

class ProfileInteractor(
    private val profileRepository: ProfileRepository
) {
    fun getProfile(): ProfileRepoModel =
        profileRepository.getProfile()
}