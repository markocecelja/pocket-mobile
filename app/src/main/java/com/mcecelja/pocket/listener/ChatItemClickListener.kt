package com.mcecelja.pocket.listener

import com.mcecelja.pocket.data.dto.chat.ChatDTO

interface ChatItemClickListener {

    fun onChatClicked(chat: ChatDTO)
}