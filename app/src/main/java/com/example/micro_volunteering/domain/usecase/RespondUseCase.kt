package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class RespondUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun respond(idTask: Int) = repository.respond(idTask)
}