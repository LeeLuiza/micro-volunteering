package com.example.micro_volunteering.domain.usecase

interface CompleteTaskUseCase {
    suspend operator fun invoke(id: Int): Boolean
}