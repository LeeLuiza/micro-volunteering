package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class LeaveFeedbackUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun leaveFeedback(idVolunteer: Int, idTask: Int, text: String, countStar: Float) =
        repository.leaveFeedback(idVolunteer, idTask, text, countStar)
}