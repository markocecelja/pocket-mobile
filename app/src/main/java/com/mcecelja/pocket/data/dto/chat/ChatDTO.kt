package com.mcecelja.pocket.data.dto.chat

import com.mcecelja.pocket.data.dto.post.PostDTO
import com.mcecelja.pocket.data.dto.users.UserDTO
import java.io.Serializable

data class ChatDTO(val id: String, val user: UserDTO, val post: PostDTO) : Serializable