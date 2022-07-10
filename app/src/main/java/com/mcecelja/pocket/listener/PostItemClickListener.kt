package com.mcecelja.pocket.listener

import com.mcecelja.pocket.data.dto.post.PostDTO

interface PostItemClickListener {

    fun onPostClicked(post: PostDTO)
}