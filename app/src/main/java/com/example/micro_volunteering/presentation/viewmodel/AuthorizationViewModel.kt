package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.R
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.usecase.AuthorizationUserUseCase
import com.example.micro_volunteering.domain.usecase.CreateTokenUseCase
import com.example.micro_volunteering.presentation.utils.ResourceProvider
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
class AuthorizationViewModel @Inject constructor(
    private val authorizationUseCase: AuthorizationUserUseCase,
    private val createTokenUseCase: CreateTokenUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _navigationRole = MutableStateFlow<UserRole?>(null)
    val navigationRole: StateFlow<UserRole?> = _navigationRole.asStateFlow()

    private val _errorText = MutableSharedFlow<String>()
    val errorText: SharedFlow<String> = _errorText.asSharedFlow()

    fun login(email: String, password: String) {
        val errors = validateInput(email, password)

        if (errors.isNotEmpty()) {
            viewModelScope.launch {
                _errorText.emit(resourceProvider.formatErrors(errors))
            }
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

    private fun validateInput(login: String, password: String) : List<Int> {
        val errors = mutableListOf<Int>()

        if (login.isBlank()) {
            errors.add(R.string.enter_full_name)
        }

        if (password.isBlank()) {
            errors.add(R.string.enter_password)
        } else if (password.length < 6) {
            errors.add(R.string.password_short)
        }

        return errors
    }

    fun onNavigationDone() {
        _navigationRole.value = null
    }
}