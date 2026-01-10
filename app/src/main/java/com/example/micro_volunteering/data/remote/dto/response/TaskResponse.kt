package com.example.micro_volunteering.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    val id: Int,
    val title: String,
    val description: String,
    val address: String,
    val organizationName: String,
    val category: String,
    val volunteersNeeded: Int,
    val date: String
)