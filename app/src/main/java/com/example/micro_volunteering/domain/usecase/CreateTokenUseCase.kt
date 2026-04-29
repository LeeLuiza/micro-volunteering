package com.example.micro_volunteering.domain.usecase

interface CreateTokenUseCase {
    suspend operator fun invoke(): Boolean
}