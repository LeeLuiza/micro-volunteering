package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.GetNotificationsUseCase
import javax.inject.Inject

class GetNotificationsUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): GetNotificationsUseCase {
    override suspend operator fun invoke() = repository.getNotifications()
}