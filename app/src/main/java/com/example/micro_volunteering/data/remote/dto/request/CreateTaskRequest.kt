package com.example.micro_volunteering.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskRequest(
    val title: String,
    val description: String,
    val address: String,
    val category: String,
    val volunteersNeeded: Int
)
