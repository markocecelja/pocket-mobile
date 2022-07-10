package com.mcecelja.pocket.data.dto.users

import java.io.Serializable

data class UserDTO(val id: String, val firstName: String, val lastName: String, val roles: List<RoleDTO>) : Serializable