package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.usecase.DismissOrganizationUseCase
import com.example.micro_volunteering.domain.usecase.UnverifiedOrganizationUseCase
import com.example.micro_volunteering.domain.usecase.VerifyOrganizationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnverifiedOrganizationViewModel @Inject constructor(
    private val unverifiedOrganizationUseCase: UnverifiedOrganizationUseCase,
    private val verifiedOrganizationUseCase: VerifyOrganizationUseCase,
    private val dismissOrganizationUseCase: DismissOrganizationUseCase
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _organization = MutableLiveData<UserProfile.Organization?>()
    val organization: LiveData<UserProfile.Organization?> = _organization

    private val _isNavigate = MutableLiveData<Boolean>(false)
    val isNavigate: LiveData<Boolean> = _isNavigate

    fun loadOrganization(id: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            _organization.value = unverifiedOrganizationUseCase.getUnverifiedOrganization(id)
            _isLoading.value = false
        }
    }

    fun verified(id: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            val isSuccess = verifiedOrganizationUseCase.verifyOrganization(id)
            _isNavigate.value = isSuccess
            _isLoading.value = false
        }
    }

    fun dismiss(id: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            val isSuccess = dismissOrganizationUseCase.dismissOrganization(id)
            _isNavigate.value = isSuccess
            _isLoading.value = false
        }
    }
}