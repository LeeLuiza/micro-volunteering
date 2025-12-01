package com.example.micro_volunteering.data.remote.dto.request


data class RegisterRequest(
    val fullName: String,
    val phone: String,
    val age: String,
    val city: String,
    val password: String
)
