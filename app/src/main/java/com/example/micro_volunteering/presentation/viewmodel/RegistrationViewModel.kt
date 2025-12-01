package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.R
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

    fun registrationVolunteer(
        fullName: String,
        phone: String,
        age: String,
        city: String,
        password: String
    ) {
        val errors = mutableListOf<Int>()

        if (fullName.isBlank()){
            errors.add(R.string.enter_full_name)
        }
        if (phone.length < 10) {
            errors.add(R.string.incorrect_phone_number)
        }

        val ageInt = age.toIntOrNull()
        if (ageInt == null || ageInt < 14) {
            errors.add(R.string.incorrect_age)
        }

        if (city.isBlank()) {
            errors.add(R.string.enter_city)
        }
        if (password.length < 6) {
            errors.add(R.string.password_short)
        }

        if (errors.isNotEmpty()) {
            _errorText.value = errors
            return
        }

        _errorText.value = emptyList()

        _isLoading.value = true
        viewModelScope.launch {
            val user = useCase.registrationUser(
                fullName,
                phone,
                age,
                city,
                password
            )

            _isLoading.value = false

            if (user != null) {
                _navigate.value = true
            }
        }
    }

    fun registrationOrganization(
        legalName: String, inn: String, address: String, orgName: String,
        managerPhone: String, email: String, phone: String, city: String,
        password: String
    ) {
        val errors = mutableListOf<Int>()

        if (legalName.isBlank()) {
            errors.add(R.string.enter_legal_name)
        }
        if (inn.length <= 10 || inn.length >= 12) {
            errors.add(R.string.incorrect_INN)
        }
        if (address.isBlank()) {
            errors.add(R.string.enter_registration_address)
        }
        if (orgName.isBlank()) {
            errors.add(R.string.enter_public_name)
        }
        if (managerPhone.length < 10) {
            errors.add(R.string.enter_phone_number)
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errors.add(R.string.incorrect_email_address)
        }

        if (phone.length < 10) {
            errors.add(R.string.incorrect_phone_number)
        }
        if (city.isBlank()) {
            errors.add(R.string.enter_city)
        }
        if (password.length < 6) {
            errors.add(R.string.password_short)
        }

        if (errors.isNotEmpty()) {
            _errorText.value = errors
            return
        }

        _errorText.value = emptyList()

        _isLoading.value = true
        /*viewModelScope.launch {
            val user = useCase.registrationUser(
                orgName,
                phone,
                city,
                password,
                inn,
                legalName,
                orgName,
                managerPhone,
                email
            )

            _isLoading.value = false

            if (user != null) {
                _navigate.value = true
            }
        }*/
    }

    fun onNavigationDone() {
        _navigate.value = false
    }
}