package com.example.micro_volunteering.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.usecase.GetUserInfoUseCase
import com.example.micro_volunteering.domain.usecase.LogoutUseCase
import com.example.micro_volunteering.domain.usecase.UploadAvatarUseCase
import com.example.micro_volunteering.domain.usecase.UploadDocumentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val uploadAvatarUseCase: UploadAvatarUseCase,
    private val uploadDocumentUseCase: UploadDocumentUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile: StateFlow<UserProfile?> = _profile

    private val _logoutSuccess = MutableSharedFlow<Boolean>()
    val logoutSuccess: SharedFlow<Boolean> = _logoutSuccess

    init {
        loadUserInfo()
    }

    fun loadUserInfo() {
        _isLoading.value = true

        viewModelScope.launch {
            _profile.value = getUserInfoUseCase()
            _isLoading.value = false
        }
    }

    fun logout() {
        _isLoading.value = true

        viewModelScope.launch {
            logoutUseCase()
            _isLoading.value = false

            _logoutSuccess.emit(true)
        }
    }

    fun uploadImage(uri: Uri) {
        viewModelScope.launch {
            uploadAvatarUseCase(uri.toString())
        }
    }

    fun uploadDocument(uri: Uri) {
        viewModelScope.launch {
            uploadDocumentUseCase(uri.toString())
        }
    }
}