package com.tla.uchattut.domain.profile

import com.tla.uchattut.data.repositories.profile.models.ProfileRepoModel

interface ProfileRepository {
    fun getProfile(): ProfileRepoModel
}