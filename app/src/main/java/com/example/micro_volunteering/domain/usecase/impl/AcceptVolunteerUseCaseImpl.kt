package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.AcceptVolunteerUseCase
import javax.inject.Inject

class AcceptVolunteerUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): AcceptVolunteerUseCase {
    override suspend operator fun invoke(idTask: Int, idVolunteer: Int) = repository.acceptVolunteer(idTask, idVolunteer)
}