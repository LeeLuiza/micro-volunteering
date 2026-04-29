package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.VerifyOrganizationUseCase
import javax.inject.Inject

class VerifyOrganizationUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): VerifyOrganizationUseCase {
    override suspend operator fun invoke(id: Int) = repository.verifyOrganization(id)
}