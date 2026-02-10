package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class VerifiedUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    fun isVerified() = repository.isVerified()
}