package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.OrganizationUnverified

interface GetUnverifiedOrganizationsUseCase {
    suspend operator fun invoke(): List<OrganizationUnverified>
}