package com.tla.uchattut.data.repositories.dialogues.models

import com.google.gson.annotations.SerializedName

data class DialoguesListRepoModel(
    @SerializedName("dialogues")
    val dialogues: List<DialoguesRepoModel>?
)