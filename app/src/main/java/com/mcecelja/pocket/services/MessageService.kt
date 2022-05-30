package com.mcecelja.pocket.services

import com.mcecelja.pocket.data.Page
import com.mcecelja.pocket.data.dto.ResponseMessage
import com.mcecelja.pocket.data.dto.chat.MessageDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessageService {

    @GET("messages")
    fun getMessages(@Query("chatId") chatId: String?): Call<ResponseMessage<Page<MessageDTO>>>

    @POST("messages")
    fun createMessage(@Body messageDTO: MessageDTO): Call<ResponseMessage<MessageDTO>>
}