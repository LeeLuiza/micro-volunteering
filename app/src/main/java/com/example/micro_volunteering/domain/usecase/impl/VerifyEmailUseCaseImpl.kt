package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.VerifyEmailUseCase
import javax.inject.Inject

class VerifyEmailUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): VerifyEmailUseCase {
    override suspend operator fun invoke(email: String, code: String) = repository.verifyEmail(email, code)
}