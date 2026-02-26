package com.example.micro_volunteering.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class VerifyEmailRequest (
    val email: String,
    val code: String
)