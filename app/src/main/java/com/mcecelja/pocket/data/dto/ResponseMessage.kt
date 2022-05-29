package com.mcecelja.pocket.data.dto


data class ResponseMessage<T>(val status: String, val errorCode: String, val errorMessage: String, val payload: T)