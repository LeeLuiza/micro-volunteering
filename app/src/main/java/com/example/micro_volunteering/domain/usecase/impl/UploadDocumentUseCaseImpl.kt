package com.example.micro_volunteering.domain.usecase.impl

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.UploadDocumentUseCase
import javax.inject.Inject

class UploadDocumentUseCaseImpl @Inject constructor(
    private val repository: VolunteeringRepository
): UploadDocumentUseCase {
    override suspend operator fun invoke(uri: String) = repository.uploadDocument(uri)
}