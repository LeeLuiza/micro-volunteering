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
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentRegistrationBinding
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
            if (args.userType == UserRole.ORGANIZATION) {
                viewModel.registrationOrganization(
                    binding.orgLegalNameEdit.text.toString(),
                    binding.orgInnEdit.text.toString(),
                    binding.orgAddressEdit.text.toString(),
                    binding.orgNameEdit.text.toString(),
                    binding.orgManagerPhoneEdit.text.toString(),
                    binding.orgEmailEdit.text.toString(),
                    binding.phoneEdit.text.toString(),
                    binding.cityEdit.text.toString(),
                    binding.passwordEdit.text.toString()
                )
            } else {
                viewModel.registrationVolunteer(
                    binding.fullNameEdit.text.toString(),
                    binding.phoneEdit.text.toString(),
                    binding.ageEdit.text.toString(),
                    binding.cityEdit.text.toString(),
                    binding.passwordEdit.text.toString(),
                )
            }
        }

        binding.authorisationButton.setOnClickListener {
            val action = RegistrationFragmentDirections.actionRegistrationFragmentToAuthorizationFragment(args.userType)
            findNavController().navigate(action)
        }

        observeViewModel()
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
                findNavController().navigate(R.id.action_registrationFragment_to_taskListFragment)
                viewModel.onNavigationDone()
            }
        }
    }
}