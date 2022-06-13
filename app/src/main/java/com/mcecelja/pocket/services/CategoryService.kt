package com.mcecelja.pocket.services

import com.mcecelja.pocket.data.Page
import com.mcecelja.pocket.data.dto.ResponseMessage
import com.mcecelja.pocket.data.dto.post.CategoryDTO
import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {

    @GET("categories")
    fun getCategories(): Call<ResponseMessage<Page<CategoryDTO>>>
}