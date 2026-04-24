package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class GetTaskRespondersUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend operator fun invoke(idTask: Int) = repository.getTaskResponders(idTask)
}