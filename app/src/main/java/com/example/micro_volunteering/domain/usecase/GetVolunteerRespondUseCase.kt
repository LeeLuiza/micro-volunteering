package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class GetVolunteerRespondUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun getVolunteerRespond(idTask: Int) = repository.getVolunteerRespond(idTask)
}