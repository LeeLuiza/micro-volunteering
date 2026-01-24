package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.usecase.LeaveFeedbackUseCase
import kotlinx.coroutines.launch
import com.example.micro_volunteering.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LeaveFeedbackViewModel @Inject constructor(
    private val useCase: LeaveFeedbackUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _errorText = MutableLiveData<Int?>()
    val errorText: LiveData<Int?> = _errorText

    fun leaveFeedback(idVolunteer: Int, idTask: Int, message: String, rating: Float) {
        _errorText.value = null

        if (rating == 0f) {
            _errorText.value = R.string.error_no_rating
            return
        }

        if (message.isBlank()) {
            _errorText.value = R.string.error_empty_feedback
            return
        }

        if (message.trim().length < 5) {
            _errorText.value = R.string.error_short_feedback
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            _isSuccess.value = useCase.leaveFeedback(idVolunteer, idTask, message, rating)
            _isLoading.value = false
        }
    }
}