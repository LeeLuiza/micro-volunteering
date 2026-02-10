package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class UnverifiedOrganizationListUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun getUnverifiedOrganizationList() = repository.getUnverifiedOrganizationList()
}