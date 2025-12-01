package com.example.micro_volunteering.domain.repository

import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.model.User

interface VolunteeringRepository {
    fun registrationUser(
        fullName: String, phone: String, age: String, city: String, password: String
    ) : User?
    fun authorizationUser(login: String, password: String) : User?
    fun getTasks() : List<Task>
}