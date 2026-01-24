package com.example.micro_volunteering.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class VolunteerRespondResponse(
    val id: Int,
    val name: String,
    val avatarUrl: String?,
    val isRated: Boolean
)
