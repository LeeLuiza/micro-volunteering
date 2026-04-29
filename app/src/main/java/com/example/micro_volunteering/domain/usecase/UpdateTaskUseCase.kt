package com.example.micro_volunteering.domain.usecase

interface UpdateTaskUseCase {
    suspend operator fun invoke(
        id: Int, title: String, description: String, address: String, category: String, volunteersNeeded: Int
    ) : Int?
}