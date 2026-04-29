package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.GetUnverifiedOrganizationUseCase
import javax.inject.Inject

class GetUnverifiedOrganizationUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): GetUnverifiedOrganizationUseCase {
    override suspend operator fun invoke(id: Int) = repository.getUnverifiedOrganization(id)
}