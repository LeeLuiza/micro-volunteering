package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.micro_volunteering.databinding.FragmentRegistrationBinding
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.presentation.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegistrationViewModel by viewModels()

    private val args: RegistrationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.registerButton.setOnClickListener {
            callViewModel()
        }

        binding.authorizationButton.setOnClickListener {
            val action = RegistrationFragmentDirections.actionRegistrationFragmentToAuthorizationFragment(args.userType)
            findNavController().navigate(action)
        }

        displayData()
        observeViewModel()
    }

    private fun displayData() {
        if (args.userType == UserRole.ORGANIZATION) {
            binding.containerVolunteer.isVisible = false
            binding.containerOrganization.isVisible = true
        }
        else {
            binding.containerVolunteer.isVisible = true
            binding.containerOrganization.isVisible = false
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.isVisible = true
                binding.content.isVisible = false
            }
        }

        viewModel.errorText.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                binding.errorText.text = error.joinToString("\n") { id -> "- ${getString(id)}" }
                binding.errorText.isVisible = true
            } else {
                binding.errorText.isVisible = false
            }
        }

        viewModel.navigate.observe(viewLifecycleOwner) { isNavigate ->
            if (isNavigate) {
                val action = RegistrationFragmentDirections.actionRegistrationFragmentToTaskListFragment()
                findNavController().navigate(action)
                viewModel.onNavigationDone()
            }
        }
    }

    fun callViewModel() {
        val user = if (args.userType == UserRole.ORGANIZATION) {
            UserProfile.Organization(
                binding.orgLegalNameEdit.text.toString(),
                binding.orgInnEdit.text.toString(),
                binding.orgAddressEdit.text.toString(),
                binding.orgNameEdit.text.toString(),
                binding.orgManagerPhoneEdit.text.toString(),
                binding.phoneEdit.text.toString(),
                binding.orgEmailEdit.text.toString(),
                binding.cityEdit.text.toString(),
                false,
                binding.passwordEdit.text.toString()
            )
        } else {
            val ageString = binding.ageEdit.text.toString()
            val ageInt = ageString.toIntOrNull() ?: 0

            UserProfile.Volunteer(
                binding.fullNameEdit.text.toString(),
                binding.passwordEdit.text.toString(),
                binding.phoneEdit.text.toString(),
                binding.mailEdit.text.toString(),
                ageInt,
                binding.cityEdit.text.toString(),
            )
        }
        viewModel.registration(user)
    }
}