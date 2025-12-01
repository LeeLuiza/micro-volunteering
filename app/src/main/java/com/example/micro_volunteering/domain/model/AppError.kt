package com.example.micro_volunteering.domain.model

sealed class AppError {
    object NoInternet : AppError()
    data class ServerError(val code: Int) : AppError()
    object Unknown : AppError()
}