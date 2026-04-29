package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.model.UpdateProfileParams

interface UpdateUserInfoUseCase {
    suspend operator fun invoke(userInfo: UpdateProfileParams) : Boolean
}