package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.usecase.CompleteTaskUseCase
import com.example.micro_volunteering.domain.usecase.GetTaskDetailsUseCase
import com.example.micro_volunteering.domain.usecase.GetUserRoleUseCase
import com.example.micro_volunteering.domain.usecase.RespondToTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTaskDetailsUseCase: GetTaskDetailsUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val respondToTaskUseCase: RespondToTaskUseCase
) : ViewModel() {
    private val _task = MutableStateFlow<Task?>(null)
    val task: StateFlow<Task?> = _task

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isComplete = MutableSharedFlow<Boolean>()
    val isComplete: SharedFlow<Boolean> = _isComplete

    private val _isRespond = MutableSharedFlow<Boolean>()
    val isRespond: SharedFlow<Boolean> = _isRespond

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
            _isComplete.emit(completeTaskUseCase(id))
            _isLoading.value = false
        }
    }

    fun respond(idTask: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            _isRespond.emit(respondToTaskUseCase(idTask))
            _isLoading.value = false
        }
    }

    fun isUserOrganization(): Boolean {
        return getUserRoleUseCase() == UserRole.ORGANIZATION
    }
}