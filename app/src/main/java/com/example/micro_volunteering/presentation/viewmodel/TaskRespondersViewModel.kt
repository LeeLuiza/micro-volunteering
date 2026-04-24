package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.VolunteerRespond
import com.example.micro_volunteering.domain.usecase.GetTaskRespondersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskRespondersViewModel @Inject constructor(
    private val getTaskRespondersUseCase: GetTaskRespondersUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _volunteerResponders = MutableLiveData<List<VolunteerRespond>>()
    val volunteerResponders: LiveData<List<VolunteerRespond>> = _volunteerResponders

    fun loadResponders(idTask: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            _volunteerResponders.value = getTaskRespondersUseCase(idTask)
            _isLoading.value = false
        }
    }
}