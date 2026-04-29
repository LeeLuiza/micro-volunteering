package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.CheckOrganizationVerifiedUseCase
import javax.inject.Inject

class CheckOrganizationVerifiedUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): CheckOrganizationVerifiedUseCase {
    override operator fun invoke() = repository.isVerified()
}