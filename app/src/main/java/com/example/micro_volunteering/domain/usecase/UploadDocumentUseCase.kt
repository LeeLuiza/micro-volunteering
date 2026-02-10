package com.example.micro_volunteering.domain.usecase

import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class UploadDocumentUseCase @Inject constructor(
    private val repository: VolunteeringRepository
) {
    suspend fun uploadDocument(uri: String): Boolean {
        return repository.uploadDocument(uri)
    }
}