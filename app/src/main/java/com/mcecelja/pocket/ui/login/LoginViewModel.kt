package com.mcecelja.pocket.ui.login

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.mcecelja.pocket.ui.pocket.PocketActivity
import com.mcecelja.pocket.Pocket
import com.mcecelja.pocket.data.PreferenceManager
import com.mcecelja.pocket.data.dto.ResponseMessage
import com.mcecelja.pocket.data.dto.users.RegisterRequestDTO
import com.mcecelja.pocket.data.dto.users.UserLoginRequestDTO
import com.mcecelja.pocket.data.dto.users.UserLoginResponseDTO
import com.mcecelja.pocket.enums.PreferenceEnum
import com.mcecelja.pocket.services.AuthenticationService
import com.mcecelja.pocket.ui.LoadingViewModel
import com.mcecelja.pocket.utils.ErrorUtil
import com.mcecelja.pocket.utils.RestUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : LoadingViewModel() {

    fun loginUser(activity: Activity, userLoginRequestDTO: UserLoginRequestDTO) {

        val apiCall =
            RestUtil.createService(AuthenticationService::class.java).loginUser(userLoginRequestDTO)

        changeVisibility(View.VISIBLE)

        apiCall.enqueue(object : Callback<ResponseMessage<UserLoginResponseDTO>> {
            override fun onResponse(
                call: Call<ResponseMessage<UserLoginResponseDTO>>,
                response: Response<ResponseMessage<UserLoginResponseDTO>>
            ) {

                changeVisibility(View.INVISIBLE)

                if (response.isSuccessful) {

                    if (response.body()?.errorCode != null) {
                        ErrorUtil.showAlertMessageForErrorCode(
                            response.body()!!.errorCode,
                            activity
                        )

                        ErrorUtil.handleError(response.body()!!.errorCode, activity)
                    } else {
                        PreferenceManager.savePreference(
                            PreferenceEnum.TOKEN,
                            response.body()?.payload?.jwt
                        )
                        val intent = Intent(Pocket.application, PocketActivity::class.java)
                        activity.startActivity(intent)
                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseMessage<UserLoginResponseDTO>>,
                t: Throwable
            ) {
                changeVisibility(View.INVISIBLE)

                Toast.makeText(
                    Pocket.application,
                    "User login failed!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }

    fun registerUser(activity: Activity, registerRequestDTO: RegisterRequestDTO) {

        val apiCall =
            RestUtil.createService(AuthenticationService::class.java)
                .registerUser(registerRequestDTO)

        apiCall.enqueue(object : Callback<ResponseMessage<String>> {
            override fun onResponse(
                call: Call<ResponseMessage<String>>,
                response: Response<ResponseMessage<String>>
            ) {
                if (response.isSuccessful) {

                    if (response.body()?.errorCode != null) {
                        ErrorUtil.showAlertMessageForErrorCode(
                            response.body()!!.errorCode,
                            activity
                        )

                        ErrorUtil.handleError(response.body()!!.errorCode, activity)
                    } else {
                        loginUser(
                            activity,
                            UserLoginRequestDTO(
                                registerRequestDTO.username,
                                registerRequestDTO.password
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ResponseMessage<String>>, t: Throwable) {
                Toast.makeText(
                    Pocket.application,
                    "User registration failed!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }
}