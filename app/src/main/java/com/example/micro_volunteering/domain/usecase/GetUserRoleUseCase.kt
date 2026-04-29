package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.UserRole

interface GetUserRoleUseCase {
    operator fun invoke(): UserRole?
}