package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.Task

interface GetTaskDetailsUseCase {
    suspend operator fun invoke(id: Int) : Task?
}