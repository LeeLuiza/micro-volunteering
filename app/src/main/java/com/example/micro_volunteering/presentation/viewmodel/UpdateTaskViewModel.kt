package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.usecase.DeleteTaskUseCase
import com.example.micro_volunteering.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateTaskViewModel @Inject constructor(
    private val useCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _taskId = MutableLiveData<Int?>()
    val taskId: LiveData<Int?> = _taskId

    private val _isSuccessDeleteTask = MutableLiveData<Boolean>()
    val isSuccessDeleteTask: LiveData<Boolean> = _isSuccessDeleteTask

    fun updateTask(
        id: Int, title: String, description: String, address: String, category: String, volunteersNeeded: Int
    ) {
        _isLoading.value = true

        viewModelScope.launch {
            val result = useCase.updateTask(id, title, description, address, category, volunteersNeeded)
            _taskId.value = result
            _isLoading.value = false
        }
    }

    fun deleteTask(id: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            val result = deleteTaskUseCase.deleteTask(id)
            _isSuccessDeleteTask.value = result
            _isLoading.value = false
        }
    }
}