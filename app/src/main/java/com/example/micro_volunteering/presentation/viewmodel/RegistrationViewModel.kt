package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.R
import com.example.micro_volunteering.domain.model.UserProfileRegister
import com.example.micro_volunteering.domain.usecase.RegistrationUserUseCase
import com.example.micro_volunteering.presentation.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUserUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _navigate = MutableSharedFlow<String?>()
    val navigate: SharedFlow<String?> = _navigate

    private val _errorText = MutableSharedFlow<String>()
    val errorText: SharedFlow<String> = _errorText

    fun registerOrganization(
        legalName: String,
        inn: String,
        address: String,
        name: String,
        managerPhone: String,
        phone: String,
        email: String,
        city: String,
        password: String
    ) {
        val user = UserProfileRegister.Organization(
            legalName, inn, address, name, managerPhone, phone, email, city, false, password
        )
        registerUser(user)
    }

    fun registerVolunteer(
        fullName: String,
        password: String,
        phone: String,
        email: String,
        ageRaw: String,
        city: String
    ) {
        val age = ageRaw.toIntOrNull() ?: 0

        val user = UserProfileRegister.Volunteer(
            fullName, password, phone, email, age, city
        )
        registerUser(user)
    }

    private fun registerUser(user: UserProfileRegister) {

        val errors = when (user) {
            is UserProfileRegister.Volunteer -> validateVolunteer(user)
            is UserProfileRegister.Organization -> validateOrganization(user)
        }

        if (errors.isNotEmpty()) {
            viewModelScope.launch {
                _errorText.emit(resourceProvider.formatErrors(errors))
            }
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            val isSuccess  = registrationUseCase(user)
            _isLoading.value = false

            if (isSuccess) {
                _navigate.emit(user.email)
            }
        }
    }

    private fun validateVolunteer(user: UserProfileRegister.Volunteer) : List<Int> {
        val errors = mutableListOf<Int>()

        if (user.name.isBlank()) errors.add(R.string.enter_full_name)
        if (user.phone.length < 10) errors.add(R.string.incorrect_phone_number)
        if (user.age < 14) errors.add(R.string.incorrect_age)
        if (!isEmailValid(user.email)) errors.add(R.string.incorrect_email_address)
        if (user.city.isBlank()) errors.add(R.string.enter_city)
        if (user.password.length < 6) errors.add(R.string.password_short)

        return errors
    }

    private fun validateOrganization(user: UserProfileRegister.Organization) : List<Int> {
        val errors = mutableListOf<Int>()

        if (user.legalName.isBlank()) errors.add(R.string.enter_legal_name)
        if (user.inn.length != 10 && user.inn.length != 12) errors.add(R.string.incorrect_INN)
        if (user.legalAddress.isBlank()) errors.add(R.string.enter_registration_address)
        if (user.displayName.isBlank()) errors.add(R.string.enter_public_name)
        if (user.managerPhone.length < 10) errors.add(R.string.enter_phone_number)
        if (!isEmailValid(user.email)) errors.add(R.string.incorrect_email_address)
        if (user.phoneOrg.length < 10) errors.add(R.string.incorrect_phone_number)
        if (user.city.isBlank()) errors.add(R.string.enter_city)
        if (user.password.length < 6) errors.add(R.string.password_short)

        return errors
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}