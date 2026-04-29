package com.example.micro_volunteering.presentation.ui

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.micro_volunteering.databinding.FragmentUnverifiedOrganizationBinding
import com.example.micro_volunteering.presentation.viewmodel.UnverifiedOrganizationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class UnverifiedOrganizationFragment : BaseFragment<FragmentUnverifiedOrganizationBinding, UnverifiedOrganizationViewModel>(
    FragmentUnverifiedOrganizationBinding::inflate
) {

    override val viewModel: UnverifiedOrganizationViewModel by viewModels()
    private val args: UnverifiedOrganizationFragmentArgs by navArgs()

    override fun setupViews() {
        setupListener()
    }

    private fun setupListener() {
        binding.btnVerified.setOnClickListener {
            viewModel.verify()
        }

        binding.btnNotVerified.setOnClickListener {
            viewModel.reject()
        }
    }

    override fun observeViewModel() {
        collectFlow(viewModel.isLoading) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.content.isVisible = !isLoading
        }

        collectFlow(viewModel.organization) { organization ->
            organization?.let {
                with(binding) {
                    legalName.text = organization.legalName
                    legalAddress.text = organization.legalAddress
                    inn.text = organization.inn
                    mailOrg.text = organization.email
                    cityOrg.text = organization.city
                    legalAddress.text = organization.legalAddress
                    displayName.text = organization.displayName
                    managerPhone.text = organization.managerPhone
                    phoneOrg.text = organization.phoneOrg
                    imgDocument.load(organization.documentUrl)
                }
            }
        }

        collectFlow(viewModel.isNavigate) { isNavigate ->
            if (isNavigate) {
                findNavController().popBackStack()
            }
        }
    }

    override fun loadData() {
        viewModel.loadOrganization(args.id)
    }
}