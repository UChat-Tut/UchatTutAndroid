package com.tla.uchattut.data.network.model

import com.google.gson.annotations.SerializedName

data class MinimalUserNetworkModel(

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String? = "",

    @SerializedName("surname")
    val surname: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("address")
    val address: String?

)