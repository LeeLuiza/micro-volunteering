package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class RegistrationUserUseCase @Inject constructor(
    private val repository: VolunteeringRepository
){
    suspend fun registrationUser(user: UserProfile) : Boolean {
        return when (user) {
            is UserProfile.Organization -> repository.registrationOrganization(user)
            is UserProfile.Volunteer -> repository.registrationVolunteer(user)
        }
    }
}