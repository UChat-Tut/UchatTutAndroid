package com.tla.uchattut.data.repositories.dialogues.models

import com.google.gson.annotations.SerializedName

data class DialogueRepoModel(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("last_message")
    val lastMessage: LastMessageModel?,

    val imageUrl: String?,

    var unreadMessageCount: Int?
) {
    data class LastMessageModel(
        @SerializedName("sender")
        val sender: Int?,

        @SerializedName("message")
        val message: String?,

        val time: String?
    )
}