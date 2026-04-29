package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.SetNotificationPermissionRequestedUseCase
import javax.inject.Inject

class SetNotificationPermissionRequestedUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): SetNotificationPermissionRequestedUseCase {
    override suspend operator fun invoke() = repository.setNotificationPermissionRequested()
}