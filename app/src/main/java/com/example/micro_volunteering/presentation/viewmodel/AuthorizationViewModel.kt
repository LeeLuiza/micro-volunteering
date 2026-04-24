package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.R
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.usecase.AuthorizationUserUseCase
import com.example.micro_volunteering.domain.usecase.CreateTokenUseCase
import com.example.micro_volunteering.presentation.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authorizationUseCase: AuthorizationUserUseCase,
    private val createTokenUseCase: CreateTokenUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _navigationRole = MutableLiveData<UserRole?>()
    val navigationRole: LiveData<UserRole?> = _navigationRole

    private val _errorText = MutableLiveData<String>()
    val errorText: LiveData<String> = _errorText

    fun login(email: String, password: String) {
        val isValid = validateInput(email, password)

        if (!isValid) {
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            val userRole = authorizationUseCase(email, password)

            if (userRole != null) {
                createTokenUseCase()

                _navigationRole.value = userRole
            }

            _isLoading.value = false
        }
    }

    private fun validateInput(login: String, password: String) : Boolean {
        val errors = mutableListOf<Int>()

        if (login.isBlank()) {
            errors.add(R.string.enter_full_name)
        }

        if (password.isBlank()) {
            errors.add(R.string.enter_password)
        } else if (password.length < 6) {
            errors.add(R.string.password_short)
        }

        _errorText.value = resourceProvider.formatErrors(errors)

        return errors.isEmpty()
    }

    fun onNavigationDone() {
        _navigationRole.value = null
    }
}