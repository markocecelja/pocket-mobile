package com.mcecelja.pocket.services

import com.mcecelja.pocket.data.dto.ResponseMessage
import com.mcecelja.pocket.data.dto.users.RegisterRequestDTO
import com.mcecelja.pocket.data.dto.users.UserLoginRequestDTO
import com.mcecelja.pocket.data.dto.users.UserLoginResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST("public/authentication/login")
    fun loginUser(@Body userLoginRequestDTO: UserLoginRequestDTO): Call<ResponseMessage<UserLoginResponseDTO>>

    @POST("public/authentication/registration")
    fun registerUser(@Body registerRequestDTO: RegisterRequestDTO): Call<ResponseMessage<String>>
}