package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class UserLoggedUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    fun isUserLogged() : Boolean = repository.isUserLogged()
}