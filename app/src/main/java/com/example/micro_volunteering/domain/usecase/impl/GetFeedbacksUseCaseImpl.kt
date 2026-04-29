package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.GetFeedbacksUseCase
import javax.inject.Inject

class GetFeedbacksUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): GetFeedbacksUseCase {
    override suspend operator fun invoke() = repository.getFeedbacks()
}