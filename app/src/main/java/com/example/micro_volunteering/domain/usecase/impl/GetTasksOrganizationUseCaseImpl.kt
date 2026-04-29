package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.model.TaskStatus
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.GetTasksOrganizationUseCase
import javax.inject.Inject

class GetTasksOrganizationUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): GetTasksOrganizationUseCase {
    override suspend operator fun invoke(status: TaskStatus) = repository.getTasksOrganization(status)
}