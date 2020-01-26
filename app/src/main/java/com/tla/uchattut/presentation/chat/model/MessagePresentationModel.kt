package com.tla.uchattut.presentation.chat.model

data class MessagePresentationModel(
    val type: Type,
    val id: Int = 0,
    val senderId: String?,
    val text: String?,
    val time: String?,
    var isLastMessageInBlock: Boolean? = null
) {
    enum class Type {
        OUT, IN, DATE
    }
}