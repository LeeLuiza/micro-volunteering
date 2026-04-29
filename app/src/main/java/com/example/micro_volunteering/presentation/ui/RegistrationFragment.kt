package com.example.micro_volunteering.presentation.ui

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.micro_volunteering.databinding.FragmentRegistrationBinding
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.presentation.utils.navigate
import com.example.micro_volunteering.presentation.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding, RegistrationViewModel>(FragmentRegistrationBinding::inflate) {

    override val viewModel: RegistrationViewModel by viewModels()
    private val args: RegistrationFragmentArgs by navArgs()

    override fun setupViews() {
        displayData()
        setupListeners()
    }

    private fun setupListeners() {
        binding.registerButton.setOnClickListener {
            onRegisterClicked()
        }

        binding.authorizationButton.setOnClickListener {
            navigate(RegistrationFragmentDirections.actionRegistrationFragmentToAuthorizationFragment(args.userType))
        }
    }

    private fun displayData() {
        binding.containerVolunteer.isVisible = args.userType == UserRole.VOLUNTEER
        binding.containerOrganization.isVisible = args.userType == UserRole.ORGANIZATION
    }

    override fun observeViewModel() {
        collectFlow(viewModel.isLoading) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.content.isVisible = !isLoading
        }

        collectFlow(viewModel.errorText) { errorText ->
            binding.errorText.text = errorText
            binding.errorText.isVisible = errorText.isNotEmpty()
        }

        collectFlow(viewModel.navigate) { email ->
            email?.let {
                navigate(RegistrationFragmentDirections.actionRegistrationFragmentToVerifyEmailFragment(email))
            }
        }
    }

    fun onRegisterClicked() {
        with(binding) {
            if (args.userType == UserRole.ORGANIZATION) {
                viewModel.registerOrganization(
                    legalName = orgLegalNameEdit.text.toString(),
                    inn = orgInnEdit.text.toString(),
                    address = orgAddressEdit.text.toString(),
                    name = orgNameEdit.text.toString(),
                    managerPhone = orgManagerPhoneEdit.text.toString(),
                    phone = phoneEdit.text.toString(),
                    email = orgEmailEdit.text.toString(),
                    city = cityEdit.text.toString(),
                    password = passwordEdit.text.toString()
                )
            } else {
                viewModel.registerVolunteer(
                    fullName = fullNameEdit.text.toString(),
                    password = passwordEdit.text.toString(),
                    phone = phoneEdit.text.toString(),
                    email = mailEdit.text.toString(),
                    ageRaw = ageEdit.text.toString(),
                    city = cityEdit.text.toString()
                )
            }
        }
    }
}