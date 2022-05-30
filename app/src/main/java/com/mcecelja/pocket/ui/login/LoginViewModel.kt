package com.mcecelja.pocket.ui.login

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mcecelja.pocket.Pocket
import com.mcecelja.pocket.data.dto.ResponseMessage
import com.mcecelja.pocket.data.dto.users.RegisterRequestDTO
import com.mcecelja.pocket.data.dto.users.UserLoginRequestDTO
import com.mcecelja.pocket.data.dto.users.UserLoginResponseDTO
import com.mcecelja.pocket.services.AuthenticationService
import com.mcecelja.pocket.ui.BaseViewModel
import com.mcecelja.pocket.utils.RestUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : BaseViewModel() {

    private val _token: MutableLiveData<String> = MutableLiveData<String>()
    val token: LiveData<String> = _token

    fun loginUser(userLoginRequestDTO: UserLoginRequestDTO) {

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
                        setError(response.body()!!.errorCode)
                    } else {
                        _token.postValue(response.body()?.payload?.jwt)
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

    fun registerUser(registerRequestDTO: RegisterRequestDTO) {

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
                        setError(response.body()!!.errorCode)
                    } else {
                        loginUser(
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