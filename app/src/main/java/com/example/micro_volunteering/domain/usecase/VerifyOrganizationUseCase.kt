package com.example.micro_volunteering.domain.usecase

interface VerifyOrganizationUseCase {
    suspend operator fun invoke(id: Int): Boolean
}