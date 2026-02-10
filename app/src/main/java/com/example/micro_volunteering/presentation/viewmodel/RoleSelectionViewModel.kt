package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.usecase.GetUserRoleUseCase
import com.example.micro_volunteering.domain.usecase.UserLoggedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RoleSelectionViewModel @Inject constructor(
    private val userLoggedUseCase: UserLoggedUseCase,
    private val userRoleUseCase: GetUserRoleUseCase
) : ViewModel() {

    fun isUserLogged() : Boolean {
        return userLoggedUseCase.isUserLogged()
    }

    fun getUserRole() : UserRole? {
        return userRoleUseCase.getUserRole()
    }
}