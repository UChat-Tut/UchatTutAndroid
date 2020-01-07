package com.tla.uchattut.data.repositories.chat.models

import com.google.gson.annotations.SerializedName

data class ChatRepoModel(
    @SerializedName("messages")
    val messages: List<MessageRepoModel>
)