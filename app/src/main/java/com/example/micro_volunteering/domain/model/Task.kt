package com.example.micro_volunteering.domain.model

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val address: String,
    val organizationName: String,
    val category: String,
    val volunteersNeeded: Int
)
