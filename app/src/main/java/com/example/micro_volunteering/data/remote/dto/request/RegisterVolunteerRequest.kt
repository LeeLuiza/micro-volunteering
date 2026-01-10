package com.example.micro_volunteering.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterVolunteerRequest(
    val fullName: String,
    val phone: String,
    val age: String,
    val city: String,
    val email: String,
    val password: String
)