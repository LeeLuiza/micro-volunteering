package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.Feedback

interface GetFeedbacksUseCase {
    suspend operator fun invoke(): List<Feedback>
}