package com.example.micro_volunteering.domain.usecase

interface AcceptVolunteerUseCase {
    suspend operator fun invoke(idTask: Int, idVolunteer: Int): Boolean
}