package com.example.micro_volunteering.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class OrganizationProfileRequest(
    val legalName: String,
    val inn: String,
    val legalAddress: String,
    val displayName: String,
    val managerPhone: String,
    val phoneOrg: String,
    val email: String,
    val city: String
)