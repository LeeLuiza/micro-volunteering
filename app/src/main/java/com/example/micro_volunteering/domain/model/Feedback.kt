package com.example.micro_volunteering.domain.model

data class Feedback(
    val id: Int,
    val text: String,
    val nameUser: String,
    val countStars: Float
)
