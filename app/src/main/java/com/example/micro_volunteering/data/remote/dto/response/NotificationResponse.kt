package com.example.micro_volunteering.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
    val avatarUrl: String?,
    val idUser: Int,
    val message: String,
    val createdAt: String,
    val taskName: String,
    val idTask: Int
)
