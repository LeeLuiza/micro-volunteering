package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.GetUnverifiedOrganizationsUseCase
import javax.inject.Inject

class GetUnverifiedOrganizationsUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): GetUnverifiedOrganizationsUseCase {
    override suspend operator fun invoke() = repository.getUnverifiedOrganizations()
}