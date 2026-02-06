package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.R
import com.example.micro_volunteering.domain.model.CategoryTask
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

    private val _errorText = MutableLiveData<List<Int>>()
    val errorText: LiveData<List<Int>> = _errorText

    fun createTask(
        title: String, description: String, address: String, selectedPositionCategory: Int, volunteersNeeded: String
    ) {
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

        if (errors.isNotEmpty()) {
            _errorText.value = errors
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            val result = useCase.createTask(
                title, description, address, CategoryTask.entries[selectedPositionCategory].category, volunteersInt!!
            )
            _taskId.value = result
            _isLoading.value = false
        }
    }
}