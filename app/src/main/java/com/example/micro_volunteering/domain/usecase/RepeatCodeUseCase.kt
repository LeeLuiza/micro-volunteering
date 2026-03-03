package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class RepeatCodeUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun repeatCode(email: String) = repository.repeatCode(email)
}