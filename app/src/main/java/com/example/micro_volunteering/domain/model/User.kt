package com.example.micro_volunteering.domain.model

data class User(
    val id: Int,
    val name: String,
    val password: String,
    val phone: String,
    val age: Int,
    val city: String
)
