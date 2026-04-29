package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.RejectVolunteerUseCase
import javax.inject.Inject

class RejectVolunteerUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): RejectVolunteerUseCase {
    override suspend operator fun invoke(idTask: Int, idVolunteer: Int) = repository.rejectVolunteer(idTask, idVolunteer)
}