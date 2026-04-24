package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.usecase.RejectOrganizationUseCase
import com.example.micro_volunteering.domain.usecase.GetUnverifiedOrganizationUseCase
import com.example.micro_volunteering.domain.usecase.VerifyOrganizationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnverifiedOrganizationViewModel @Inject constructor(
    private val getUnverifiedOrganizationUseCase: GetUnverifiedOrganizationUseCase,
    private val verifyOrganizationUseCase: VerifyOrganizationUseCase,
    private val rejectOrganizationUseCase: RejectOrganizationUseCase
) : ViewModel() {
    private var currentOrganizationId: Int? = null

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _organization = MutableLiveData<UserProfile.Organization?>()
    val organization: LiveData<UserProfile.Organization?> = _organization

    private val _isNavigate = MutableLiveData<Boolean>(false)
    val isNavigate: LiveData<Boolean> = _isNavigate

    fun loadOrganization(id: Int) {
        currentOrganizationId = id

        _isLoading.value = true

        viewModelScope.launch {
            _organization.value = getUnverifiedOrganizationUseCase(id)
            _isLoading.value = false
        }
    }

    fun verify() {
        currentOrganizationId?.let { id ->
            _isLoading.value = true
            viewModelScope.launch {
                val isSuccess = verifyOrganizationUseCase(id)
                _isNavigate.value = isSuccess
                _isLoading.value = false
            }
        }
    }

    fun reject() {
        currentOrganizationId?.let { id ->
            _isLoading.value = true
            viewModelScope.launch {
                val isSuccess = rejectOrganizationUseCase(id)
                _isNavigate.value = isSuccess
                _isLoading.value = false
            }
        }
    }
}