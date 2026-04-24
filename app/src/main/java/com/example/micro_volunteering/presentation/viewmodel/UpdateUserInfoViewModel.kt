package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.UpdateProfileParams
import com.example.micro_volunteering.domain.usecase.DeleteUserUseCase
import com.example.micro_volunteering.domain.usecase.UpdateUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpdateUserInfoViewModel @Inject constructor(
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _updateSuccess = MutableLiveData<Boolean>()
    val updateSuccess: LiveData<Boolean> = _updateSuccess

    private val _deleteSuccess = MutableLiveData<Boolean>()
    val deleteSuccess: LiveData<Boolean> = _deleteSuccess

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
            _updateSuccess.value = isSuccess

            _isLoading.value = false
        }
    }

    fun deleteAccount() {
        _isLoading.value = true

        viewModelScope.launch {
            val isSuccess = deleteUserUseCase()
            _deleteSuccess.value = isSuccess

            _isLoading.value = false
        }
    }
}