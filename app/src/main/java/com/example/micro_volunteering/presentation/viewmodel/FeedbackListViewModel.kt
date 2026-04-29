package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.Feedback
import com.example.micro_volunteering.domain.usecase.GetFeedbacksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackListViewModel @Inject constructor(
    private val getFeedbacksUseCase: GetFeedbacksUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _feedbacks = MutableStateFlow<List<Feedback>>(emptyList())
    val feedbacks: StateFlow<List<Feedback>> = _feedbacks

    init {
        loadFeedbacks()
    }

    fun loadFeedbacks() {
        _isLoading.value = true

        viewModelScope.launch {
            val result  = getFeedbacksUseCase()
            _feedbacks.value  = result
            _isLoading.value = false
        }
    }
}