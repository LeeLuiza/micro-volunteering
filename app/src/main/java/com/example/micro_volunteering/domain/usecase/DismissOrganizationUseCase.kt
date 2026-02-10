package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class DismissOrganizationUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun dismissOrganization(id: Int) = repository.dismissOrganization(id)
}