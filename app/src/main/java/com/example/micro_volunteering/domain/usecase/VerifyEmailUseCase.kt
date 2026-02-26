package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class VerifyEmailUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun verifyEmail(email: String, code: String) = repository.verifyEmail(email, code)
}