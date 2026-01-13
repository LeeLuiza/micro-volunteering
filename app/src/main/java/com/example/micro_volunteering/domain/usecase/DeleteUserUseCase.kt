package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class DeleteUserUseCase  @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun deleteUser() = repository.deleteUser()
}