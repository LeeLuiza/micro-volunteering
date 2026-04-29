package com.example.micro_volunteering.domain.usecase

interface UploadAvatarUseCase {
    suspend operator fun invoke(uri: String): Boolean
}