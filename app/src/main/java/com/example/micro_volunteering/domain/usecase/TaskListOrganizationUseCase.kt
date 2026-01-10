package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class TaskListOrganizationUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun getTasksOrganization() : List<Task> {
        return repository.getTasksOrganization()
    }
}