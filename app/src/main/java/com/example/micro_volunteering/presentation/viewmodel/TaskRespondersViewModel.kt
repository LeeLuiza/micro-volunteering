package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.VolunteerRespond
import com.example.micro_volunteering.domain.usecase.GetTaskRespondersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskRespondersViewModel @Inject constructor(
    private val getTaskRespondersUseCase: GetTaskRespondersUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _volunteerResponders = MutableStateFlow<List<VolunteerRespond>>(emptyList())
    val volunteerResponders: StateFlow<List<VolunteerRespond>> = _volunteerResponders

    fun loadResponders(idTask: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            _volunteerResponders.value = getTaskRespondersUseCase(idTask)
            _isLoading.value = false
        }
    }
}