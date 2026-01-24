package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class CompleteTaskUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun completeTask(id: Int) = repository.completeTask(id)
}