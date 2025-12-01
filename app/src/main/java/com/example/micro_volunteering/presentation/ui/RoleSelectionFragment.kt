package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.micro_volunteering.databinding.FragmentRoleSelectionBinding
import com.example.micro_volunteering.domain.model.UserRole

class RoleSelectionFragment : Fragment() {

    private lateinit var binding: FragmentRoleSelectionBinding

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