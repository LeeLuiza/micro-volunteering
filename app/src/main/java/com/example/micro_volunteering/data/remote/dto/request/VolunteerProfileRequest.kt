package com.example.micro_volunteering.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class VolunteerProfileRequest (
    val name: String,
    val phone: String,
    val email: String,
    val age: Int,
    val city: String
)