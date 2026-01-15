package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.UpdateProfileParams
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun updateUserInfo(userInfo: UpdateProfileParams) : Boolean {
        return when (userInfo) {
            is UpdateProfileParams.Volunteer -> {
                repository.updateVolunteerInfo(userInfo)
            }
            is UpdateProfileParams.Organization -> {
                repository.updateOrganizationInfo(userInfo)
            }
        }
    }
}