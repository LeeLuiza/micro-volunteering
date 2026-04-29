package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.RespondToTaskUseCase
import javax.inject.Inject

class RespondToTaskUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): RespondToTaskUseCase {
    override suspend operator fun invoke(idTask: Int) = repository.respondToTask(idTask)
}