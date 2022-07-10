package com.mcecelja.pocket.ui.post

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mcecelja.pocket.Pocket
import com.mcecelja.pocket.data.PreferenceManager
import com.mcecelja.pocket.data.dto.ResponseMessage
import com.mcecelja.pocket.data.dto.post.PostDTO
import com.mcecelja.pocket.enums.PreferenceEnum
import com.mcecelja.pocket.services.PostService
import com.mcecelja.pocket.ui.BaseViewModel
import com.mcecelja.pocket.utils.RestUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostViewModel : BaseViewModel() {

    private val _post: MutableLiveData<PostDTO> = MutableLiveData<PostDTO>()
    val post: LiveData<PostDTO> = _post

    fun setPost(post: PostDTO) {

        val apiCall =
            RestUtil.createService(
                PostService::class.java,
                PreferenceManager.getPreference(PreferenceEnum.TOKEN)
            ).getPost(post.id)

        changeVisibility(View.VISIBLE)

        apiCall.enqueue(object : Callback<ResponseMessage<PostDTO>> {
            override fun onResponse(
                call: Call<ResponseMessage<PostDTO>>,
                response: Response<ResponseMessage<PostDTO>>
            ) {

                changeVisibility(View.INVISIBLE)

                if (response.isSuccessful) {

                    if (response.body()?.errorCode != null) {
                        setError(response.body()!!.errorCode)
                    } else {
                        _post.postValue(response.body()?.payload)
                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseMessage<PostDTO>>,
                t: Throwable
            ) {

                changeVisibility(View.INVISIBLE)

                Toast.makeText(
                    Pocket.application,
                    "Get post failed!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }
}