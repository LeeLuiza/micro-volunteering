package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.UpdateProfileParams
import com.example.micro_volunteering.domain.usecase.DeleteUserUseCase
import com.example.micro_volunteering.domain.usecase.UpdateUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpdateUserInfoViewModel @Inject constructor(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _updateSuccess = MutableSharedFlow<Boolean>()
    val updateSuccess: SharedFlow<Boolean> = _updateSuccess

    private val _deleteSuccess = MutableSharedFlow<Boolean>()
    val deleteSuccess: SharedFlow<Boolean> = _deleteSuccess

    fun updateVolunteer(
        name: String,
        phone: String,
        email: String,
        ageRaw: String,
        city: String
    ) {
        val age = ageRaw.toIntOrNull() ?: 0

        val params = UpdateProfileParams.Volunteer(name, phone, email, age, city)
        updateUserInfo(params)
    }

    fun updateOrganization(
        legalName: String,
        inn: String,
        legalAddress: String,
        displayName: String,
        managerPhone: String,
        phone: String,
        email: String,
        city: String
    ) {
        val params = UpdateProfileParams.Organization(
            legalName, inn, legalAddress, displayName, managerPhone, phone, email, city
        )
        updateUserInfo(params)
    }

    private fun updateUserInfo(user: UpdateProfileParams) {
        _isLoading.value = true

        viewModelScope.launch {
            val isSuccess = updateUserInfoUseCase(user)
            _updateSuccess.emit(isSuccess)

            _isLoading.value = false
        }
    }

    fun deleteAccount() {
        _isLoading.value = true

        viewModelScope.launch {
            val isSuccess = deleteUserUseCase()
            _deleteSuccess.emit(isSuccess)

            _isLoading.value = false
        }
    }
}