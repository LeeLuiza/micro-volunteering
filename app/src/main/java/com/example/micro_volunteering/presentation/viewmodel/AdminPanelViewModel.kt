package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.OrganizationUnverified
import com.example.micro_volunteering.domain.usecase.LogoutUseCase
import com.example.micro_volunteering.domain.usecase.GetUnverifiedOrganizationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminPanelViewModel @Inject constructor(
    private val getUnverifiedOrganizationsUseCase: GetUnverifiedOrganizationsUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _unverifiedOrganizations = MutableLiveData<List<OrganizationUnverified>>()
    val unverifiedOrganizations: LiveData<List<OrganizationUnverified>> = _unverifiedOrganizations

    private val _logoutSuccess = MutableLiveData<Boolean>()
    val logoutSuccess: LiveData<Boolean> = _logoutSuccess

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

            _logoutSuccess.value = true
        }
    }
}