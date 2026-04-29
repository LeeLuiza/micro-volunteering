package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.UserRole

interface AuthorizationUserUseCase {
    suspend operator fun invoke(login: String, password: String): UserRole?
}