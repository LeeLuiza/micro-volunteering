package com.example.micro_volunteering.domain.usecase

interface UploadDocumentUseCase {
    suspend operator fun invoke(uri: String): Boolean
}