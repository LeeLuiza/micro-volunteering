package com.example.micro_volunteering.data.remote.dto.response

import com.example.micro_volunteering.domain.model.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse (
    val id: Int,
    val role: UserRole
)