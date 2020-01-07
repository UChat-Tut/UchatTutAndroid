package com.tla.uchattut.data.repositories.chat.models

import com.google.gson.annotations.SerializedName
import com.tla.uchattut.data.repositories.chat.models.response.ResponseMessageRepoModel

data class ChatRepoModel(
    @SerializedName("messages")
    val messages: List<ResponseMessageRepoModel>
)