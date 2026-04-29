package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.NotificationPermissionRequestedUseCase
import javax.inject.Inject

class NotificationPermissionRequestedUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): NotificationPermissionRequestedUseCase {
    override suspend operator fun invoke() = repository.isNotificationPermissionRequested()
}