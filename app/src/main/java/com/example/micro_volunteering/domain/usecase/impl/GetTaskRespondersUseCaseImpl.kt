package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.GetTaskRespondersUseCase
import javax.inject.Inject

class GetTaskRespondersUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): GetTaskRespondersUseCase {
    override suspend operator fun invoke(idTask: Int) = repository.getTaskResponders(idTask)
}