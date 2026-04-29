package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.model.UpdateProfileParams
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.UpdateUserInfoUseCase
import javax.inject.Inject

class UpdateUserInfoUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): UpdateUserInfoUseCase {
    override suspend operator fun invoke(userInfo: UpdateProfileParams) : Boolean {
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