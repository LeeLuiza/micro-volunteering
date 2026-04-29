package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.UploadAvatarUseCase
import javax.inject.Inject

class UploadAvatarUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): UploadAvatarUseCase {
    override suspend operator fun invoke(uri: String) = repository.uploadAvatar(uri)
}