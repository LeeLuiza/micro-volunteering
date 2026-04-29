package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.CompleteTaskUseCase
import javax.inject.Inject

class CompleteTaskUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): CompleteTaskUseCase {
    override suspend operator fun invoke(id: Int) = repository.completeTask(id)
}