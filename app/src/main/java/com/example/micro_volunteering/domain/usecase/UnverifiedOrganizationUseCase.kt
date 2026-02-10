package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class UnverifiedOrganizationUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun getUnverifiedOrganization(id: Int) = repository.getUnverifiedOrganization(id)
}