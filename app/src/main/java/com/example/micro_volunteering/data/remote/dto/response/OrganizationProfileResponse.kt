package com.example.micro_volunteering.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class OrganizationProfileResponse (
    val legalName: String,
    val avatarUrl: String?,
    val inn: String,
    val legalAddress: String,
    val displayName: String,
    val managerPhone: String,
    val phoneOrg: String,
    val email: String,
    val city: String,
    val isVerified: Boolean,
    val password: String
)