package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.OrganizationUnverified
import com.example.micro_volunteering.domain.usecase.GetUnverifiedOrganizationsUseCase
import com.example.micro_volunteering.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminPanelViewModel @Inject constructor(
    private val getUnverifiedOrganizationsUseCase: GetUnverifiedOrganizationsUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _unverifiedOrganizations = MutableStateFlow<List<OrganizationUnverified>>(emptyList())
    val unverifiedOrganizations: StateFlow<List<OrganizationUnverified>> = _unverifiedOrganizations.asStateFlow()

    private val _logoutSuccess = MutableSharedFlow<Boolean>()
    val logoutSuccess: SharedFlow<Boolean> = _logoutSuccess.asSharedFlow()

    init {
        loadUnverifiedOrganizations()
    }

    fun loadUnverifiedOrganizations() {
        _isLoading.value = true

        viewModelScope.launch {
            _unverifiedOrganizations.value = getUnverifiedOrganizationsUseCase()
            _isLoading.value = false
        }
    }

    fun logout() {
        _isLoading.value = true

        viewModelScope.launch {
            logoutUseCase()
            _isLoading.value = false

            _logoutSuccess.emit(true)
        }
    }
}