package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun getNotification() = repository.getNotification()
}