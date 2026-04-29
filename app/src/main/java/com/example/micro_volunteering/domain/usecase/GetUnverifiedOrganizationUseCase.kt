package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.UserProfile

interface GetUnverifiedOrganizationUseCase {
    suspend operator fun invoke(id: Int): UserProfile.Organization?
}