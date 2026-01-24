package com.example.micro_volunteering.domain.model

data class Notification(
    val avatarUrl: String?,
    val idUser: Int,
    val message: String,
    val createdAt: String,
    val idTask: Int,
    val taskName: String
)
