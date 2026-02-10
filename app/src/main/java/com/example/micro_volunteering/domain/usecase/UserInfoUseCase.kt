package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.model.UserProfileRegister
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class UserInfoUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun getUserInfo() : UserProfile? {
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