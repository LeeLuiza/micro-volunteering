package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.micro_volunteering.databinding.FragmentRoleSelectionBinding
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.presentation.viewmodel.RoleSelectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoleSelectionFragment : Fragment() {

    private lateinit var binding: FragmentRoleSelectionBinding
    private val viewModel: RoleSelectionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoleSelectionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isUserLogged = viewModel.isUserLogged()
        val role = viewModel.getUserRole()

        if (isUserLogged && role != null) {
            when (role) {
                UserRole.ADMIN -> {
                    val action = RoleSelectionFragmentDirections.actionRoleSelectionFragmentToAdminPanelFragment()
                    findNavController().navigate(action)
                }
                UserRole.ORGANIZATION, UserRole.VOLUNTEER -> {
                    val action = RoleSelectionFragmentDirections.actionRoleSelectionFragmentToTaskListFragment()
                    findNavController().navigate(action)
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
        val action = RoleSelectionFragmentDirections.actionRoleSelectionFragmentToRegistrationFragment(role)
        findNavController().navigate(action)
    }
}