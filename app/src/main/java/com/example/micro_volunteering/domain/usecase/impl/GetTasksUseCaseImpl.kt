package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.GetTasksUseCase
import javax.inject.Inject

class GetTasksUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): GetTasksUseCase {
    override suspend operator fun invoke() = repository.getTasks()
}