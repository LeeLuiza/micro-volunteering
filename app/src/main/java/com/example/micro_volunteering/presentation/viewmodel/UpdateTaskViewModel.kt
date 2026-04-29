package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.R
import com.example.micro_volunteering.domain.model.CategoryTask
import com.example.micro_volunteering.domain.usecase.DeleteTaskUseCase
import com.example.micro_volunteering.domain.usecase.UpdateTaskUseCase
import com.example.micro_volunteering.presentation.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.toIntOrNull

@HiltViewModel
class UpdateTaskViewModel @Inject constructor(
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _taskId = MutableSharedFlow<Int?>()
    val taskId: SharedFlow<Int?> = _taskId

    private val _isSuccessDeleteTask = MutableSharedFlow<Boolean>()
    val isSuccessDeleteTask: SharedFlow<Boolean> = _isSuccessDeleteTask

    private val _errorText = MutableSharedFlow<String>()
    val errorText: SharedFlow<String> = _errorText

    fun updateTask(
        id: Int,
        title: String,
        description: String,
        address: String,
        selectedPositionCategory: Int,
        volunteersNeeded: String
    ) {
        val errors = validateInput(title, description, address, selectedPositionCategory, volunteersNeeded)

        if (errors.isNotEmpty()) {
            viewModelScope.launch {
                _errorText.emit(resourceProvider.formatErrors(errors))
            }
            return
        }

        val volunteersInt = volunteersNeeded.toInt()

        _isLoading.value = true

        viewModelScope.launch {
            val result = updateTaskUseCase(
                id, title, description, address, CategoryTask.entries[selectedPositionCategory].category, volunteersInt
            )
            _taskId.emit(result)
            _isLoading.value = false
        }
    }

    fun deleteTask(id: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            val result = deleteTaskUseCase(id)
            _isSuccessDeleteTask.emit(result)
            _isLoading.value = false
        }
    }

    private fun validateInput(
        title: String,
        description: String,
        address: String,
        selectedPositionCategory: Int,
        volunteersNeeded: String
    ) : List<Int> {
        val errors = mutableListOf<Int>()

        if (title.isBlank()) {
            errors.add(R.string.error_empty_title)
        }

        if (description.isBlank()) {
            errors.add(R.string.error_empty_description)
        } else if (description.length < 10) {
            errors.add(R.string.error_short_description)
        }

        if (address.isBlank()) {
            errors.add(R.string.error_empty_address)
        }

        if (selectedPositionCategory == -1) {
            errors.add(R.string.error_empty_category)
        }

        val volunteersInt = volunteersNeeded.toIntOrNull()

        if (volunteersInt == null || volunteersInt <= 0) {
            errors.add(R.string.error_invalid_volunteers)
        }

        return errors
    }
}