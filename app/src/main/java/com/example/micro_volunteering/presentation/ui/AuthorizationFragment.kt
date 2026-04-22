package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.micro_volunteering.databinding.FragmentAuthorizationBinding
import com.example.micro_volunteering.presentation.viewmodel.AuthorizationViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.navArgs
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.presentation.extensions.navigate

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
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupListeners()
    }

    private fun setupListeners() {
        binding.login.setOnClickListener {
            viewModel.authorizationUser(
                binding.fullNameEdit.text.toString(),
                binding.passwordEdit.text.toString()
            )
        }

        binding.registerButton.setOnClickListener {
            val action = AuthorizationFragmentDirections.actionAuthorizationFragmentToRegistrationFragment(args.userType)
            navigate(action)
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.isVisible = true
                binding.content.isVisible = false
            }
            else
            {
                binding.progressBar.isVisible = false
                binding.content.isVisible = true
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

        viewModel.navigationRole.observe(viewLifecycleOwner) { role ->
            if (role != null) {
                val action = when (role) {
                    UserRole.ADMIN ->
                        AuthorizationFragmentDirections.actionAuthorizationFragmentToAdminPanelFragment()

                    UserRole.VOLUNTEER, UserRole.ORGANIZATION ->
                        AuthorizationFragmentDirections.actionAuthorizationFragmentToTaskListFragment()
                }

                navigate(action)
                viewModel.onNavigationDone()
            }
        }
    }
}