package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.model.UserProfileRegister
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class RegistrationUserUseCase @Inject constructor(
    private val repository: VolunteeringRepository
){
    suspend fun registrationUser(user: UserProfileRegister) : Boolean {
        return when (user) {
            is UserProfileRegister.Organization -> repository.registrationOrganization(user)
            is UserProfileRegister.Volunteer -> repository.registrationVolunteer(user)
        }
    }
}