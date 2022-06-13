package com.mcecelja.pocket.services

import com.mcecelja.pocket.data.Page
import com.mcecelja.pocket.data.dto.ResponseMessage
import com.mcecelja.pocket.data.dto.post.PostDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {

    @GET("posts")
    fun getPosts(
        @Query("categoryId") categoryId: String?,
        @Query("title") title: String?
    ): Call<ResponseMessage<Page<PostDTO>>>
}