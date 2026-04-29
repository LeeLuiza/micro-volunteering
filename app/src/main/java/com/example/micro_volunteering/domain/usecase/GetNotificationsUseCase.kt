package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.Notification

interface GetNotificationsUseCase {
    suspend operator fun invoke(): List<Notification>
}