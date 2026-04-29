package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.DeleteTaskUseCase
import javax.inject.Inject

class DeleteTaskUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): DeleteTaskUseCase {
    override suspend operator fun invoke(id: Int) = repository.deleteTask(id)
}