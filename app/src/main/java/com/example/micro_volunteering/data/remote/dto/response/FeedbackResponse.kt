package com.example.micro_volunteering.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class FeedbackResponse(
    val id: Int,
    val text: String,
    val nameUser: String,
    val countStars: Float
)