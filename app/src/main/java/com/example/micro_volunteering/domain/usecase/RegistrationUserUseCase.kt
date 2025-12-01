package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class RegistrationUserUseCase @Inject constructor(
    private val repository: VolunteeringRepository
){
    fun registrationUser(
        fullName: String, phone: String, age: String, city: String, password: String
    ) = repository.registrationUser(fullName, phone, age, city, password)
}