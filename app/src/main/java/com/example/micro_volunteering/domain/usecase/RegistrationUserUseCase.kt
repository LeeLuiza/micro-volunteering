package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.UserProfileRegister

interface RegistrationUserUseCase {
    suspend operator fun invoke(user: UserProfileRegister) : Boolean
}