package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.OrganizationUnverified
import com.example.micro_volunteering.domain.usecase.LogoutUseCase
import com.example.micro_volunteering.domain.usecase.UnverifiedOrganizationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminPanelViewModel @Inject constructor(
    private val useCase: UnverifiedOrganizationListUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _unverifiedOrganization = MutableLiveData<List<OrganizationUnverified>>()
    val unverifiedOrganization: LiveData<List<OrganizationUnverified>> = _unverifiedOrganization

    private val _logoutSuccess = MutableLiveData<Boolean>()
    val logoutSuccess: LiveData<Boolean> = _logoutSuccess

    fun loadUnverifiedOrganizationList() {
        _isLoading.value = true

        viewModelScope.launch {
            _unverifiedOrganization.value = useCase.getUnverifiedOrganizationList()
            _isLoading.value = false
        }
    }

    fun logout() {
        _isLoading.value = true

        viewModelScope.launch {
            logoutUseCase.logout()
            _isLoading.value = false

            _logoutSuccess.value = true
        }
    }
}