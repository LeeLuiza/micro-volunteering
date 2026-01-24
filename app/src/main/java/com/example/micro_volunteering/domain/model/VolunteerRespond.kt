package com.example.micro_volunteering.domain.model

data class VolunteerRespond(
    val id: Int,
    val name: String,
    val avatarUrl: String?,
    val isRated: Boolean
)
