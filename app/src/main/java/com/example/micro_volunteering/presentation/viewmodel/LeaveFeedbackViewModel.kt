package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.usecase.LeaveFeedbackUseCase
import kotlinx.coroutines.launch
import com.example.micro_volunteering.R
import com.example.micro_volunteering.presentation.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LeaveFeedbackViewModel @Inject constructor(
    private val leaveFeedbackUseCase: LeaveFeedbackUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _errorText = MutableLiveData<String?>()
    val errorText: LiveData<String?> = _errorText

    fun leaveFeedback(idVolunteer: Int, idTask: Int, message: String, rating: Float) {

        val errors = validateInput(message, rating)

        if (errors.isNotEmpty()) {
            _errorText.value = resourceProvider.formatErrors(errors)
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            _isSuccess.value = leaveFeedbackUseCase(idVolunteer, idTask, message, rating)
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