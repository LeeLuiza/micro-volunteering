package com.example.micro_volunteering.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class VolunteerRespondRequest(
    val idTask: Int,
    val text: String,
    val countStars: Float
)