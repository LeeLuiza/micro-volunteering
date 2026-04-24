package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.usecase.CompleteTaskUseCase
import com.example.micro_volunteering.domain.usecase.GetUserRoleUseCase
import com.example.micro_volunteering.domain.usecase.RespondToTaskUseCase
import com.example.micro_volunteering.domain.usecase.GetTaskDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTaskDetailsUseCase: GetTaskDetailsUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val respondToTaskUseCase: RespondToTaskUseCase
) : ViewModel() {
    private val _task = MutableLiveData<Task?>()
    val task: LiveData<Task?> = _task

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isComplete = MutableLiveData<Boolean>(false)
    val isComplete: LiveData<Boolean> = _isComplete

    private val _isRespond = MutableLiveData<Boolean>(false)
    val isRespond: LiveData<Boolean> = _isRespond

    fun loadTask(id: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            val result = getTaskDetailsUseCase(id)
            _task.value = result
            _isLoading.value = false
        }
    }

    fun completeTask(id: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            _isComplete.value = completeTaskUseCase(id)
            _isLoading.value = false
        }
    }

    fun respond(idTask: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            _isRespond.value = respondToTaskUseCase(idTask)
            _isLoading.value = false
        }
    }

    fun isUserOrganization(): Boolean {
        return getUserRoleUseCase() == UserRole.ORGANIZATION
    }
}