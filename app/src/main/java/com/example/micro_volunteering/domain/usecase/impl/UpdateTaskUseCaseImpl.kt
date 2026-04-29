package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.UpdateTaskUseCase
import javax.inject.Inject

class UpdateTaskUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): UpdateTaskUseCase {
    override suspend operator fun invoke(
        id: Int, title: String, description: String, address: String, category: String, volunteersNeeded: Int
    ) = repository.updateTask(id, title, description, address, category, volunteersNeeded)
}