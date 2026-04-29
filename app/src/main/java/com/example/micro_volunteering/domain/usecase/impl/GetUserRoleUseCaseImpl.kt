package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.GetUserRoleUseCase
import javax.inject.Inject

class GetUserRoleUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): GetUserRoleUseCase {
    override operator fun invoke() = repository.getCurrentUserRole()
}