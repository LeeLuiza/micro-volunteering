package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class AcceptVolunteerUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun acceptVolunteer(idTask: Int, idVolunteer: Int) = repository.acceptVolunteer(idTask, idVolunteer)
}