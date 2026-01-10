package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun updateTask(
        title: String, description: String, address: String, category: String, volunteersNeeded: Int
    ) : Int? =
        repository.updateTask(title, description, address, category, volunteersNeeded)
}