package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.AuthorizationUserUseCase
import javax.inject.Inject

class AuthorizationUserUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): AuthorizationUserUseCase {
    override suspend operator fun invoke(login: String, password: String) =
        repository.login(login, password)
}