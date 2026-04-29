package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.GetUserInfoUseCase
import javax.inject.Inject

class GetUserInfoUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): GetUserInfoUseCase {
    override suspend operator fun invoke() : UserProfile? {
        val role = repository.getCurrentUserRole() ?: return null
        return when (role) {
            UserRole.VOLUNTEER -> {
                repository.getVolunteerInfo()
            }
            UserRole.ORGANIZATION -> {
                repository.getOrganizationInfo()
            }
            UserRole.ADMIN -> {
                null
            }
        }
    }
}