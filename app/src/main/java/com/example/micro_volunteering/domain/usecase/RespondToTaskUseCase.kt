package com.example.micro_volunteering.domain.usecase

interface RespondToTaskUseCase {
    suspend operator fun invoke(idTask: Int): Boolean
}