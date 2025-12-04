package com.example.micro_volunteering.domain.model

sealed class AppError {
    object NoInternet : AppError()
    data class ServerError(val code: Int) : AppError()
    data class ClientError(val code: Int, val message: String?) : AppError()
    object Unauthorized : AppError()
    object Unknown : AppError()
}