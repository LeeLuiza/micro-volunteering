package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.GetTaskDetailsUseCase
import javax.inject.Inject

class GetTaskDetailsUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): GetTaskDetailsUseCase {
    override suspend operator fun invoke(id: Int) = repository.getTask(id)
}