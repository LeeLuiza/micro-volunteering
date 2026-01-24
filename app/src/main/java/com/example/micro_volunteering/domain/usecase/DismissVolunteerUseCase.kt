package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class DismissVolunteerUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun dismissVolunteer(idTask: Int, idVolunteer: Int) = repository.dismissVolunteer(idTask, idVolunteer)
}