package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.LeaveFeedbackUseCase
import javax.inject.Inject

class LeaveFeedbackUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): LeaveFeedbackUseCase {
    override suspend operator fun invoke(idVolunteer: Int, idTask: Int, text: String, countStar: Float) =
        repository.leaveFeedback(idVolunteer, idTask, text, countStar)
}