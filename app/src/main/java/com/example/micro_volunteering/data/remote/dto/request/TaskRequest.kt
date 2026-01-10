package com.example.micro_volunteering.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class TaskRequest(
    val userId: Int,
    val title: String,
    val description: String,
    val address: String,
    val category: String,
    val volunteersNeeded: Int
)
