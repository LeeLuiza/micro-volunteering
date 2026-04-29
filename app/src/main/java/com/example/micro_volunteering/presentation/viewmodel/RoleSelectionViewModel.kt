package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.micro_volunteering.domain.usecase.CheckUserLoggedInUseCase
import com.example.micro_volunteering.domain.usecase.GetUserRoleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoleSelectionViewModel @Inject constructor(
    private val checkUserLoggedInUseCase: CheckUserLoggedInUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase
) : ViewModel() {

    fun isUserLogged() = checkUserLoggedInUseCase()
    fun getUserRole() = getUserRoleUseCase()
}