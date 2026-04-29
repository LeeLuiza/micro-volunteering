package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.Task

interface GetTasksUseCase {
    suspend operator fun invoke() : List<Task>
}