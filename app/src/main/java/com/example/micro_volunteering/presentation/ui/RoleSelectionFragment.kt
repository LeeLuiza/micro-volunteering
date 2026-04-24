package com.example.micro_volunteering.presentation.ui

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.micro_volunteering.databinding.FragmentRoleSelectionBinding
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.presentation.utils.navigate
import com.example.micro_volunteering.presentation.viewmodel.RoleSelectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoleSelectionFragment : BaseFragment<FragmentRoleSelectionBinding, RoleSelectionViewModel>(FragmentRoleSelectionBinding::inflate) {

    override val viewModel: RoleSelectionViewModel by viewModels()

    override fun setupViews() {
        val isUserLogged = viewModel.isUserLogged()
        val role = viewModel.getUserRole()

        if (isUserLogged && role != null) {
            when (role) {
                UserRole.ADMIN -> {
                    navigate(RoleSelectionFragmentDirections.actionRoleSelectionFragmentToAdminPanelFragment())
                }
                UserRole.ORGANIZATION, UserRole.VOLUNTEER -> {
                    navigate(RoleSelectionFragmentDirections.actionRoleSelectionFragmentToTaskListFragment())
                }
            }
        }
        else {
            binding.btnVolunteer.isVisible = true
            binding.btnOrganization.isVisible = true
            setupListeners()
        }
    }

    private fun setupListeners() {
        binding.btnVolunteer.setOnClickListener {
            navigateToAuth(UserRole.VOLUNTEER)
        }

        binding.btnOrganization.setOnClickListener {
            navigateToAuth(UserRole.ORGANIZATION)
        }
    }

    private fun navigateToAuth(role: UserRole) {
        navigate(RoleSelectionFragmentDirections.actionRoleSelectionFragmentToRegistrationFragment(role))
    }
}