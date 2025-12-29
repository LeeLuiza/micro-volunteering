package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.usecase.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val useCase: TaskUseCase
) : ViewModel() {
    private val _tasks = MutableLiveData<Task?>()
    val tasks: LiveData<Task?> = _tasks

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadTasks(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = useCase.getTask(id)
            _tasks.value = result
            _isLoading.value = false
        }
    }
}