package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentAuthorizationBinding
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.presentation.viewmodel.AuthorizationViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.navArgs

@AndroidEntryPoint
class AuthorizationFragment : Fragment() {
    private lateinit var binding: FragmentAuthorizationBinding
    private val viewModel: AuthorizationViewModel by viewModels()

    private val args: AuthorizationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthorizationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (args.userType == UserRole.ORGANIZATION) {
            binding.nameLogin.text = getString(R.string.email_organization)
            binding.fullNameEdit.inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        } else {
            binding.nameLogin.text = getString(R.string.enter_full_name)
        }

        binding.login.setOnClickListener {
            viewModel.authorizationUser(
                binding.fullNameEdit.text.toString(),
                binding.passwordEdit.text.toString()
            )
        }

        binding.registerButton.setOnClickListener {
            val action = AuthorizationFragmentDirections.actionAuthorizationFragmentToRegistrationFragment(args.userType)
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

        viewModel.errorText.observe(viewLifecycleOwner) { errorText ->
            if (errorText.isNotEmpty()) {
                binding.errorText.text = errorText.joinToString("\n") { id -> "- ${getString(id)}" }
                binding.errorText.isVisible = true
            }
            else {
                binding.errorText.isVisible = false
            }
        }

        viewModel.navigate.observe(viewLifecycleOwner) { isNavigate ->
            if (isNavigate) {
                findNavController().navigate(R.id.action_authorizationFragment_to_taskListFragment)
                viewModel.onNavigationDone()
            }
        }
    }
}