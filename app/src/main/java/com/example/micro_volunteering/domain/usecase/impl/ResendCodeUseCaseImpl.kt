package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.ResendCodeUseCase
import javax.inject.Inject

class ResendCodeUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): ResendCodeUseCase {
    override suspend operator fun invoke(email: String) = repository.resendCode(email)
}