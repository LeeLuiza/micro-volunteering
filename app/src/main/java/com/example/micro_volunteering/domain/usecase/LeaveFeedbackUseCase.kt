package com.example.micro_volunteering.domain.usecase

interface LeaveFeedbackUseCase {
    suspend operator fun invoke(idVolunteer: Int, idTask: Int, text: String, countStar: Float): Boolean
}