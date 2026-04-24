package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class CheckOrganizationVerifiedUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    operator fun invoke() = repository.isVerified()
}