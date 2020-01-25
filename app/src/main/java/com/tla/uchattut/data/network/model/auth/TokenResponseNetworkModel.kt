package com.tla.uchattut.data.network.model.auth

import com.google.gson.annotations.SerializedName

data class TokenResponseNetworkModel(
    @SerializedName("token")
    val token: String = ""
)