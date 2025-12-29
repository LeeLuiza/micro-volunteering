package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.usecase.TaskListOrganizationUseCase
import com.example.micro_volunteering.domain.usecase.TaskListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskListUseCase: TaskListUseCase,
    private val taskListOrganizationUseCase: TaskListOrganizationUseCase
) : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadTasks() {
        viewModelScope.launch {
            _isLoading.value = true

            val result = taskListUseCase.getTasks()
            _tasks.value = result
            _isLoading.value = false
        }
    }

    fun loadTasksOrganization() {
        viewModelScope.launch {
            _isLoading.value = true

            val result = taskListOrganizationUseCase.getTasksOrganization()
            _tasks.value = result
            _isLoading.value = false
        }
    }
}