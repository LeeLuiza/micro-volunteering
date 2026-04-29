package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.RejectOrganizationUseCase
import javax.inject.Inject

class RejectOrganizationUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): RejectOrganizationUseCase {
    override suspend operator fun invoke(id: Int) = repository.rejectOrganization(id)
}