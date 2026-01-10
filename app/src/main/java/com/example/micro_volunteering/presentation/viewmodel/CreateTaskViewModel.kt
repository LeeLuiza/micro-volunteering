package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.usecase.CreateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val useCase: CreateTaskUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _taskId = MutableLiveData<Int?>()
    val taskId: LiveData<Int?> = _taskId

    fun createTask(
        title: String, description: String, address: String, category: String, volunteersNeeded: Int
    ) {
        _isLoading.value = true

        viewModelScope.launch {
            val result = useCase.createTask(title, description, address, category, volunteersNeeded)
            _taskId.value = result
            _isLoading.value = false
        }
    }
}