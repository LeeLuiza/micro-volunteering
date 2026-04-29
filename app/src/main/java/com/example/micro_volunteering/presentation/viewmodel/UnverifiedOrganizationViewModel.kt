package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.usecase.GetUnverifiedOrganizationUseCase
import com.example.micro_volunteering.domain.usecase.RejectOrganizationUseCase
import com.example.micro_volunteering.domain.usecase.VerifyOrganizationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnverifiedOrganizationViewModel @Inject constructor(
    private val getUnverifiedOrganizationUseCase: GetUnverifiedOrganizationUseCase,
    private val verifyOrganizationUseCase: VerifyOrganizationUseCase,
    private val rejectOrganizationUseCase: RejectOrganizationUseCase
) : ViewModel() {
    private var currentOrganizationId: Int? = null

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _organization = MutableStateFlow<UserProfile.Organization?>(null)
    val organization: StateFlow<UserProfile.Organization?> = _organization

    private val _isNavigate = MutableSharedFlow<Boolean>()
    val isNavigate: SharedFlow<Boolean> = _isNavigate

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
                _isNavigate.emit(isSuccess)
                _isLoading.value = false
            }
        }
    }

    fun reject() {
        currentOrganizationId?.let { id ->
            _isLoading.value = true
            viewModelScope.launch {
                val isSuccess = rejectOrganizationUseCase(id)
                _isNavigate.emit(isSuccess)
                _isLoading.value = false
            }
        }
    }
}