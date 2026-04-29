package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.model.UserProfileRegister
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.RegistrationUserUseCase
import javax.inject.Inject

class RegistrationUserUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): RegistrationUserUseCase {
    override suspend operator fun invoke(user: UserProfileRegister) : Boolean {
        return when (user) {
            is UserProfileRegister.Organization -> repository.registerOrganization(user)
            is UserProfileRegister.Volunteer -> repository.registerVolunteer(user)
        }
    }
}