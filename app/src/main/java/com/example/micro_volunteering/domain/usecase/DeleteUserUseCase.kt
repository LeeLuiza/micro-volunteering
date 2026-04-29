package com.example.micro_volunteering.domain.usecase

interface DeleteUserUseCase {
    suspend operator fun invoke(): Boolean
}