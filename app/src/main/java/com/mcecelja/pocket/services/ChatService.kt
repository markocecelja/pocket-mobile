package com.mcecelja.pocket.services

import com.mcecelja.pocket.data.Page
import com.mcecelja.pocket.data.dto.ResponseMessage
import com.mcecelja.pocket.data.dto.chat.ChatDTO
import retrofit2.Call
import retrofit2.http.GET

interface ChatService {

    @GET("chats")
    fun getChats(): Call<ResponseMessage<Page<ChatDTO>>>
}