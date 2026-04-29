package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.DeleteUserUseCase
import javax.inject.Inject

class DeleteUserUseCaseImpl  @Inject constructor(
    private val repository: VolunteeringRepository
): DeleteUserUseCase {
    override suspend operator fun invoke() = repository.deleteUser()
}