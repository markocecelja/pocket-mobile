package com.mcecelja.pocket.ui.pocket

import android.view.View
import android.widget.Toast
import com.mcecelja.pocket.Pocket
import com.mcecelja.pocket.data.PreferenceManager
import com.mcecelja.pocket.data.dto.ResponseMessage
import com.mcecelja.pocket.data.dto.users.UserDTO
import com.mcecelja.pocket.enums.PreferenceEnum
import com.mcecelja.pocket.services.UserService
import com.mcecelja.pocket.ui.BaseViewModel
import com.mcecelja.pocket.utils.RestUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PocketViewModel : BaseViewModel() {

    fun setUser() {

        val apiCall =
            RestUtil.createService(
                UserService::class.java,
                PreferenceManager.getPreference(PreferenceEnum.TOKEN)
            ).getCurrentUserInfo()

        changeVisibility(View.VISIBLE)

        apiCall.enqueue(object : Callback<ResponseMessage<UserDTO>> {
            override fun onResponse(
                call: Call<ResponseMessage<UserDTO>>,
                response: Response<ResponseMessage<UserDTO>>
            ) {
                changeVisibility(View.INVISIBLE)

                if (response.body()?.errorCode != null) {
                    setError(response.body()!!.errorCode)
                } else if (response.isSuccessful) {
                    setUser(response.body()!!.payload)
                }
            }

            override fun onFailure(call: Call<ResponseMessage<UserDTO>>, t: Throwable) {
                changeVisibility(View.INVISIBLE)

                Toast.makeText(
                    Pocket.application,
                    "Get current user info failed!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }
}