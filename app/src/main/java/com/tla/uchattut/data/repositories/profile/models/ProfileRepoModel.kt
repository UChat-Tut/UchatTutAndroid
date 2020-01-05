package com.tla.uchattut.data.repositories.profile.models

data class ProfileRepoModel(
    val id: Int,
    val photoUrl: String? = null,
    val name: String = "",
    val phone: String? = null,
    val address: String? = null
)