package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class ResendCodeUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend operator fun invoke(email: String) = repository.resendCode(email)
}