package com.example.micro_volunteering.presentation.ui

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.micro_volunteering.databinding.FragmentAuthorizationBinding
import com.example.micro_volunteering.presentation.viewmodel.AuthorizationViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.navArgs
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.presentation.utils.navigate

@AndroidEntryPoint
class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding, AuthorizationViewModel>(FragmentAuthorizationBinding::inflate) {

    override val viewModel: AuthorizationViewModel by viewModels()
    private val args: AuthorizationFragmentArgs by navArgs()

    override fun setupViews() {
        binding.login.setOnClickListener {
            viewModel.login(
                binding.fullNameEdit.text.toString(),
                binding.passwordEdit.text.toString()
            )
        }

        binding.registerButton.setOnClickListener {
            navigate(AuthorizationFragmentDirections.actionAuthorizationFragmentToRegistrationFragment(args.userType))
        }
    }

    override fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.content.isVisible = !isLoading
        }

        viewModel.errorText.observe(viewLifecycleOwner) { errorText ->
            binding.errorText.isVisible = !errorText.isNullOrEmpty()
            errorText?.let {
                binding.errorText.text = errorText
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