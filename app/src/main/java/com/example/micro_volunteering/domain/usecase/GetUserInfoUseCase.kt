package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.UserProfile

interface GetUserInfoUseCase {
    suspend operator fun invoke() : UserProfile?
}