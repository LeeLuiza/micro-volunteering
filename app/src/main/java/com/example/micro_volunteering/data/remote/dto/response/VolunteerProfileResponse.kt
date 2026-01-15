package com.example.micro_volunteering.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class VolunteerProfileResponse (
    val name: String,
    val password: String,
    val phone: String,
    val email: String,
    val age: Int,
    val city: String,
    val countTask: Int,
    val rating: Float,
    val countFeedback: Int
)