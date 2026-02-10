package com.example.micro_volunteering.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class OrganizationUnverifiedResponse (
    val id: Int,
    val legalName: String,
    val city: String,
    val inn: String
)