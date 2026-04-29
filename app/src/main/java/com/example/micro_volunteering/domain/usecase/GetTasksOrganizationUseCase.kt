package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.model.TaskStatus

interface GetTasksOrganizationUseCase {
    suspend operator fun invoke(status: TaskStatus) : List<Task>
}