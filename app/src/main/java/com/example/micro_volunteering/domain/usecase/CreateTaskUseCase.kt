package com.example.micro_volunteering.domain.usecase

interface CreateTaskUseCase {
    suspend operator fun invoke(
        title: String, description: String, address: String,
        category: String, volunteersNeeded: Int
    ) : Int?
}