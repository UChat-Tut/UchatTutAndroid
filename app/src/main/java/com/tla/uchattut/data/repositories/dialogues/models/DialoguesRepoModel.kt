package com.tla.uchattut.data.repositories.dialogues.models

import com.google.gson.annotations.SerializedName

data class DialoguesRepoModel (
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("last_message")
    val lastMessage: LastMessageModels?,

    val imageUrl: String?,

    val unreadMessageCount: Int?
) {
    data class LastMessageModels(
        @SerializedName("sender")
        val sender: Int?,

        @SerializedName("message")
        val message: String?,

        val time: String?
    )
}