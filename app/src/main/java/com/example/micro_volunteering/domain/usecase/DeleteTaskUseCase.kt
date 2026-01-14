package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun deleteTask(id: Int) = repository.deleteTask(id)
}