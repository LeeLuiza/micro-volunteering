package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.R
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.usecase.RegistrationUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val useCase: RegistrationUserUseCase
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _navigate = MutableLiveData<Boolean>(false)
    val navigate: LiveData<Boolean> = _navigate

    private val _errorText = MutableLiveData<List<Int>>()
    val errorText: LiveData<List<Int>> = _errorText

    fun registration(user: UserProfile) {
        when (user) {
            is UserProfile.Organization -> registrationOrganization(user)
            is UserProfile.Volunteer -> registrationVolunteer(user)
        }
    }

    fun registrationVolunteer(user: UserProfile.Volunteer) {
        val errors = mutableListOf<Int>()

        if (user.name.isBlank()){
            errors.add(R.string.enter_full_name)
        }
        if (user.phone.length < 10) {
            errors.add(R.string.incorrect_phone_number)
        }

        if (user.age < 14) {
            errors.add(R.string.incorrect_age)
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            errors.add(R.string.incorrect_email_address)
        }

        if (user.city.isBlank()) {
            errors.add(R.string.enter_city)
        }
        if (user.password.length < 6) {
            errors.add(R.string.password_short)
        }

        if (errors.isNotEmpty()) {
            _errorText.value = errors
            return
        }

        _errorText.value = emptyList()

        _isLoading.value = true
        viewModelScope.launch {
            val isSuccess  = useCase.registrationUser(user)
            _isLoading.value = false

            _navigate.value = isSuccess
        }
    }

    fun registrationOrganization(user: UserProfile.Organization) {
        val errors = mutableListOf<Int>()

        if (user.legalName.isBlank()) {
            errors.add(R.string.enter_legal_name)
        }
        if (user.inn.length != 10 && user.inn.length != 12) {
            errors.add(R.string.incorrect_INN)
        }
        if (user.legalAddress.isBlank()) {
            errors.add(R.string.enter_registration_address)
        }
        if (user.displayName.isBlank()) {
            errors.add(R.string.enter_public_name)
        }
        if (user.managerPhone.length < 10) {
            errors.add(R.string.enter_phone_number)
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            errors.add(R.string.incorrect_email_address)
        }

        if (user.phoneOrg.length < 10) {
            errors.add(R.string.incorrect_phone_number)
        }
        if (user.city.isBlank()) {
            errors.add(R.string.enter_city)
        }
        if (user.password.length < 6) {
            errors.add(R.string.password_short)
        }

        if (errors.isNotEmpty()) {
            _errorText.value = errors
            return
        }

        _errorText.value = emptyList()

        _isLoading.value = true
        viewModelScope.launch {
            val isSuccess  = useCase.registrationUser(user)

            _isLoading.value = false

            if (isSuccess) {
                _navigate.value = true
            }
        }
    }

    fun onNavigationDone() {
        _navigate.value = false
    }
}