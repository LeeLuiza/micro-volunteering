package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class CreateTaskUseCase  @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun createTask(
        title: String, description: String, address: String,
        category: String, volunteersNeeded: Int
    ) : Int? =
        repository.createTask(title, description, address, category, volunteersNeeded)
}