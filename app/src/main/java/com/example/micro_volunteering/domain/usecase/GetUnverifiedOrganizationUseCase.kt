package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class GetUnverifiedOrganizationUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend operator fun invoke(id: Int) = repository.getUnverifiedOrganization(id)
}