package com.example.micro_volunteering.domain.usecase

interface RejectOrganizationUseCase {
    suspend operator fun invoke(id: Int): Boolean
}