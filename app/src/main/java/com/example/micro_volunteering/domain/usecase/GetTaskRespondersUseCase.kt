package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.VolunteerRespond

interface GetTaskRespondersUseCase {
    suspend operator fun invoke(idTask: Int): List<VolunteerRespond>
}