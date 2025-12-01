package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.R
import com.example.micro_volunteering.domain.usecase.AuthorizationUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val useCase: AuthorizationUserUseCase
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean> = _navigate

    private val _errorText = MutableLiveData<List<Int>>()
    val errorText: LiveData<List<Int>> = _errorText

    fun authorizationUser(login: String, password: String) {
        val isValid = validateInput(login, password)

        if (!isValid) {
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            val user = useCase.authorizationUser(login, password)
            _isLoading.value = false

            if (user != null) {
                _navigate.value = true
            }
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

        if (errors.isNotEmpty()) {
            _errorText.value = errors
            return false
        }

        _errorText.value = emptyList()

        return true
    }

    fun onNavigationDone() {
        _navigate.value = false
    }
}