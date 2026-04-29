package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.CheckUserLoggedInUseCase
import javax.inject.Inject

class CheckUserLoggedInUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): CheckUserLoggedInUseCase {
    override operator fun invoke() = repository.isUserLogged()
}