package com.example.micro_volunteering.domain.event

import com.example.micro_volunteering.domain.model.AppError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object NetworkErrorManager {
    private val _errorFlow = MutableSharedFlow<AppError>(extraBufferCapacity = 1)
    val errorFlow = _errorFlow.asSharedFlow()

    fun sendError(error: AppError) {
        _errorFlow.tryEmit(error)
    }
}