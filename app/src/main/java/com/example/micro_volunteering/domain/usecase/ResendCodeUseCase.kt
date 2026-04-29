package com.example.micro_volunteering.domain.usecase

interface ResendCodeUseCase {
    suspend operator fun invoke(email: String): Boolean
}