package com.mcecelja.pocket.ui.chat

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mcecelja.pocket.Pocket
import com.mcecelja.pocket.data.Page
import com.mcecelja.pocket.data.PreferenceManager
import com.mcecelja.pocket.data.dto.ResponseMessage
import com.mcecelja.pocket.data.dto.chat.ChatDTO
import com.mcecelja.pocket.data.dto.chat.MessageDTO
import com.mcecelja.pocket.enums.PreferenceEnum
import com.mcecelja.pocket.services.MessageService
import com.mcecelja.pocket.ui.BaseViewModel
import com.mcecelja.pocket.utils.RestUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessagesViewModel : BaseViewModel() {

    private val _chat: MutableLiveData<ChatDTO> = MutableLiveData<ChatDTO>()
    val chat: LiveData<ChatDTO> = _chat

    private val _messages: MutableLiveData<List<MessageDTO>> = MutableLiveData<List<MessageDTO>>()
    val messages: LiveData<List<MessageDTO>> = _messages

    fun setChat(chat: ChatDTO) {
        _chat.postValue(chat)
    }

    fun setMessages(chatId: String) {

        val apiCall =
            RestUtil.createService(
                MessageService::class.java,
                PreferenceManager.getPreference(PreferenceEnum.TOKEN)
            ).getMessages(chatId)

        changeVisibility(View.VISIBLE)

        apiCall.enqueue(object : Callback<ResponseMessage<Page<MessageDTO>>> {
            override fun onResponse(
                call: Call<ResponseMessage<Page<MessageDTO>>>,
                response: Response<ResponseMessage<Page<MessageDTO>>>
            ) {

                changeVisibility(View.INVISIBLE)

                if (response.isSuccessful) {

                    if (response.body()?.errorCode != null) {
                        setError(response.body()!!.errorCode)
                    } else {
                        _messages.postValue(response.body()?.payload?.content)
                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseMessage<Page<MessageDTO>>>,
                t: Throwable
            ) {

                changeVisibility(View.INVISIBLE)

                Toast.makeText(
                    Pocket.application,
                    "Get messages failed!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }

    fun sendMessage(text: String) {

        val message = MessageDTO(text, _chat.value!!)

        val apiCall =
            RestUtil.createService(
                MessageService::class.java,
                PreferenceManager.getPreference(PreferenceEnum.TOKEN)
            ).createMessage(message)

        apiCall.enqueue(object : Callback<ResponseMessage<MessageDTO>> {
            override fun onResponse(
                call: Call<ResponseMessage<MessageDTO>>,
                response: Response<ResponseMessage<MessageDTO>>
            ) {

                if (response.isSuccessful) {

                    if (response.body()?.errorCode != null) {
                        setError(response.body()!!.errorCode)
                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseMessage<MessageDTO>>,
                t: Throwable
            ) {

                Toast.makeText(
                    Pocket.application,
                    "Send message failed!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }

    fun addMessage(messageDTO: MessageDTO) {
        val newMessages: MutableList<MessageDTO> = mutableListOf()
        newMessages.add(messageDTO)
        _messages.value!!.forEach { newMessages.add(it) }
        _messages.postValue(newMessages)
    }
}