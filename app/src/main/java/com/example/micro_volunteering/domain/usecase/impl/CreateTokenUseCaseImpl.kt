package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.CreateTokenUseCase
import javax.inject.Inject

class CreateTokenUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): CreateTokenUseCase {
    override suspend operator fun invoke() = repository.createFcmToken()
}