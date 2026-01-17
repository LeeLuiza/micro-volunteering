package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class UploadAvatarUseCase @Inject constructor(
    private val repository: VolunteeringRepository)
{
    suspend fun uploadAvatar(uri: String): Boolean {
        return repository.uploadPhoto(uri)
    }
}