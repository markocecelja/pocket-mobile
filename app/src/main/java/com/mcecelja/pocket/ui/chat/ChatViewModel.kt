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
import com.mcecelja.pocket.enums.PreferenceEnum
import com.mcecelja.pocket.services.ChatService
import com.mcecelja.pocket.ui.BaseViewModel
import com.mcecelja.pocket.utils.RestUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel : BaseViewModel() {

    private val _chats: MutableLiveData<List<ChatDTO>> = MutableLiveData<List<ChatDTO>>()
    val chats: LiveData<List<ChatDTO>> = _chats

    fun setChats() {

        val apiCall =
            RestUtil.createService(
                ChatService::class.java,
                PreferenceManager.getPreference(PreferenceEnum.TOKEN)
            ).getChats()

        changeVisibility(View.VISIBLE)

        apiCall.enqueue(object : Callback<ResponseMessage<Page<ChatDTO>>> {
            override fun onResponse(
                call: Call<ResponseMessage<Page<ChatDTO>>>,
                response: Response<ResponseMessage<Page<ChatDTO>>>
            ) {

                changeVisibility(View.INVISIBLE)

                if (response.isSuccessful) {

                    if (response.body()?.errorCode != null) {
                        setError(response.body()!!.errorCode)
                    } else {
                        _chats.value = response.body()?.payload?.content
                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseMessage<Page<ChatDTO>>>,
                t: Throwable
            ) {

                changeVisibility(View.INVISIBLE)

                Toast.makeText(
                    Pocket.application,
                    "Get chats failed!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }
}