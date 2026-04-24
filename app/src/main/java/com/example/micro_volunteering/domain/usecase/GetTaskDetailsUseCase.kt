package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class GetTaskDetailsUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend operator fun invoke(id: Int) : Task? {
        return repository.getTask(id)
    }
}