package com.example.micro_volunteering.domain.usecase

interface VerifyEmailUseCase {
    suspend operator fun invoke(email: String, code: String): Boolean
}