package com.example.micro_volunteering.domain.usecase

interface NotificationPermissionRequestedUseCase {
    suspend operator fun invoke(): Boolean
}