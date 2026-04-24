package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.UserProfileRegister
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class RegistrationUserUseCase @Inject constructor(
    private val repository: VolunteeringRepository
){
    suspend operator fun invoke(user: UserProfileRegister) : Boolean {
        return when (user) {
            is UserProfileRegister.Organization -> repository.registerOrganization(user)
            is UserProfileRegister.Volunteer -> repository.registerVolunteer(user)
        }
    }
}