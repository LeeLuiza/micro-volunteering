package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class GetUserRoleUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    fun getUserRole(): UserRole? {
        return repository.getCurrentUserRole()
    }
}