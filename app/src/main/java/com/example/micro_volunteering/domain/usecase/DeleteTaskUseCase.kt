package com.example.micro_volunteering.domain.usecase

interface DeleteTaskUseCase {
    suspend operator fun invoke(id: Int): Boolean
}