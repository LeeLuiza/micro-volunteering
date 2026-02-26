package com.example.micro_volunteering.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.usecase.UpdateTokenUseCase
import com.example.micro_volunteering.domain.usecase.VerifyEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val useCase: VerifyEmailUseCase,
    private val tokenUseCase: UpdateTokenUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>(false)
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun verifyEmail(email: String, code: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val isSuccess = useCase.verifyEmail(email, code)

            if (isSuccess) {
                tokenUseCase.updateToken()
            }
            
            _isSuccess.value = isSuccess
            _isLoading.value = false
        }
    }
}