package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.Feedback
import com.example.micro_volunteering.domain.usecase.GetFeedbacksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackListViewModel @Inject constructor(
    private val useCase: GetFeedbacksUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _feedbacks = MutableLiveData<List<Feedback>>()
    val feedbacks: LiveData<List<Feedback>> = _feedbacks

    fun loadFeedback() {
        _isLoading.value = true

        viewModelScope.launch {
            val result  = useCase.getFeedback()
            _feedbacks.value  = result
            _isLoading.value = false
        }
    }
}