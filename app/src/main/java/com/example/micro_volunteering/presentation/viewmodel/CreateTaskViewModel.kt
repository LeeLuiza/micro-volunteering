package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.R
import com.example.micro_volunteering.domain.model.CategoryTask
import com.example.micro_volunteering.domain.usecase.CreateTaskUseCase
import com.example.micro_volunteering.presentation.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _taskId = MutableLiveData<Int?>()
    val taskId: LiveData<Int?> = _taskId

    private val _errorText = MutableLiveData<String>()
    val errorText: LiveData<String> = _errorText

    fun createTask(
        title: String, description: String, address: String, selectedPositionCategory: Int, volunteersNeeded: String
    ) {
        val errors = validateInput(title, description, address, selectedPositionCategory, volunteersNeeded)

        if (errors.isNotEmpty()) {
            _errorText.value = resourceProvider.formatErrors(errors)
            return
        }
        val volunteersInt = volunteersNeeded.toInt()

        _isLoading.value = true

        viewModelScope.launch {
            val result = createTaskUseCase(
                title, description, address, CategoryTask.entries[selectedPositionCategory].category, volunteersInt
            )
            _taskId.value = result
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

        if (title.isBlank()) errors.add(R.string.error_empty_title)

        if (description.isBlank()) {
            errors.add(R.string.error_empty_description)
        } else if (description.length < 10) {
            errors.add(R.string.error_short_description)
        }

        if (address.isBlank()) errors.add(R.string.error_empty_address)
        if (selectedPositionCategory == -1) errors.add(R.string.error_empty_category)
        val volunteersInt = volunteersNeeded.toIntOrNull()
        if (volunteersInt == null || volunteersInt <= 0) errors.add(R.string.error_invalid_volunteers)

        return errors
    }
}