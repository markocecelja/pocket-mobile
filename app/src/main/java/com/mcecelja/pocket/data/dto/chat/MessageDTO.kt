package com.mcecelja.pocket.data.dto.chat

data class MessageDTO(
    val id: String,
    val text: String,
    val createdBy: MessageCreatorDTO?,
    val chat: ChatDTO,
    val createdDateTime: String
) {
    constructor(text: String, chat: ChatDTO) : this("", text, null, chat, "")
}