package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.micro_volunteering.R
import com.example.micro_volunteering.domain.usecase.LeaveFeedbackUseCase
import com.example.micro_volunteering.presentation.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LeaveFeedbackViewModel @Inject constructor(
    private val leaveFeedbackUseCase: LeaveFeedbackUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isSuccess = MutableSharedFlow<Boolean>()
    val isSuccess: SharedFlow<Boolean> = _isSuccess

    private val _errorText = MutableSharedFlow<String>()
    val errorText: SharedFlow<String> = _errorText

    fun leaveFeedback(idVolunteer: Int, idTask: Int, message: String, rating: Float) {

        val errors = validateInput(message, rating)

        if (errors.isNotEmpty()) {
            viewModelScope.launch {
                _errorText.emit(resourceProvider.formatErrors(errors))
            }
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            _isSuccess.emit(leaveFeedbackUseCase(idVolunteer, idTask, message, rating))
            _isLoading.value = false
        }
    }

    private fun validateInput(message: String, rating: Float) : List<Int> {
        val errors = mutableListOf<Int>()

        if (rating == 0f) errors.add(R.string.error_no_rating)

        if (message.isBlank()) {
            errors.add(R.string.error_empty_feedback)
        } else if (message.trim().length < 5) {
            errors.add(R.string.error_short_feedback)
        }

        return errors
    }
}