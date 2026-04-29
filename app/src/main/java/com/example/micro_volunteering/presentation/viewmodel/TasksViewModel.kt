package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.CategoryTask
import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.model.TaskStatus
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.usecase.CheckOrganizationVerifiedUseCase
import com.example.micro_volunteering.domain.usecase.GetTasksOrganizationUseCase
import com.example.micro_volunteering.domain.usecase.GetTasksUseCase
import com.example.micro_volunteering.domain.usecase.GetUserRoleUseCase
import com.example.micro_volunteering.domain.usecase.NotificationPermissionRequestedUseCase
import com.example.micro_volunteering.domain.usecase.SetNotificationPermissionRequestedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val getTasksOrganizationUseCase: GetTasksOrganizationUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val notificationPermissionRequestedUseCase: NotificationPermissionRequestedUseCase,
    private val setNotificationPermissionRequestedUseCase: SetNotificationPermissionRequestedUseCase,
    private val checkOrganizationVerifiedUseCase: CheckOrganizationVerifiedUseCase
) : ViewModel() {
    private var allTasks: List<Task> = emptyList()
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isNotificationPermissionRequested = MutableStateFlow<Boolean>(false)
    val isNotificationPermissionRequested: StateFlow<Boolean> = _isNotificationPermissionRequested

    private val _selectedTab = MutableStateFlow<TaskStatus>(TaskStatus.ACTIVE)
    val selectedTab: StateFlow<TaskStatus> = _selectedTab

    init {
        loadTasks()
        checkPermissionStatus()
    }

    fun loadTasks() {
        if (isUserOrganization()) {
            val status = _selectedTab.value
            loadTasksOrganization(status)
        } else {
            loadTasksVolunteer()
        }
    }

    fun changeTab(status: TaskStatus) {
        if (_selectedTab.value != status) {
            _selectedTab.value = status
            loadTasksOrganization(status)
        }
    }

    fun loadTasksVolunteer() {
        _isLoading.value = true

        viewModelScope.launch {
            val result = getTasksUseCase()
            _tasks.value = result
            allTasks = result
            _isLoading.value = false
        }
    }

    fun loadTasksOrganization(status: TaskStatus) {
        _isLoading.value = true

        viewModelScope.launch {
            val result = getTasksOrganizationUseCase(status)
            _tasks.value = result
            allTasks = result
            _isLoading.value = false
        }
    }

    fun checkPermissionStatus() {
        viewModelScope.launch {
            val result = notificationPermissionRequestedUseCase()
            _isNotificationPermissionRequested.value = !result
        }
    }

    fun searchTasks(input: String?) {
        if (input.isNullOrBlank()) {
            _tasks.value = allTasks
        }
        else {
            _tasks.value = allTasks.filter { task ->
                task.title.contains(input, ignoreCase = true) || task.description.contains(input, ignoreCase = true)
            }
        }
    }

    fun filterTasks(category: CategoryTask?) {
        if (category == null) {
            _tasks.value = allTasks
        }
        else {
            _tasks.value = allTasks.filter { it.category == category }
        }
    }

    fun isUserOrganization(): Boolean {
        return getUserRoleUseCase() == UserRole.ORGANIZATION
    }

    fun setNotificationPermissionRequested() {
        viewModelScope.launch {
            setNotificationPermissionRequestedUseCase()
        }
    }

    fun isVerified(): Boolean {
        return checkOrganizationVerifiedUseCase()
    }
}