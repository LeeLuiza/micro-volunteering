package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.VolunteerRespond
import com.example.micro_volunteering.domain.usecase.GetVolunteerRespondUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VolunteerRespondViewModel @Inject constructor(
    private val useCase: GetVolunteerRespondUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _volunteerRespond = MutableLiveData<List<VolunteerRespond>>()
    val volunteerRespond: LiveData<List<VolunteerRespond>> = _volunteerRespond

    fun loadVolunteerRespond(idTask: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            _volunteerRespond.value = useCase.getVolunteerRespond(idTask)
            _isLoading.value = false
        }
    }
}