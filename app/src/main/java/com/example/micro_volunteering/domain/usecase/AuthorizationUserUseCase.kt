package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class AuthorizationUserUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    fun authorizationUser(login: String, password: String) =
        repository.authorizationUser(login, password)
}