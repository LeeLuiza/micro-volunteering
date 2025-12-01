package com.example.micro_volunteering.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse (
    val id: Int,
    val name: String,
    val password: String,
    val phone: String,
    val age: Int,
    val city: String
)