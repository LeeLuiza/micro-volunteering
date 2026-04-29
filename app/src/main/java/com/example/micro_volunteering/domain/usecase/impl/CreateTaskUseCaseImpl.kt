package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.CreateTaskUseCase
import javax.inject.Inject

class CreateTaskUseCaseImpl  @Inject constructor(
    private val repository: VolunteeringRepository
): CreateTaskUseCase {
    override suspend operator fun invoke(
        title: String, description: String, address: String,
        category: String, volunteersNeeded: Int
    ) = repository.createTask(title, description, address, category, volunteersNeeded)
}