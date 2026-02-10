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
import coil.load
import com.example.micro_volunteering.databinding.FragmentUnverifiedOrganizationBinding
import com.example.micro_volunteering.presentation.viewmodel.UnverifiedOrganizationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class UnverifiedOrganizationFragment : Fragment() {

    private lateinit var binding: FragmentUnverifiedOrganizationBinding
    private val viewModel: UnverifiedOrganizationViewModel by viewModels()

    private val args: UnverifiedOrganizationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUnverifiedOrganizationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idOrganization = args.id

        viewModel.loadOrganization(idOrganization)
        observeViewModel(idOrganization)
        setupListener()
    }

    private fun observeViewModel(idOrganization: Int) {
        binding.btnVerified.setOnClickListener {
            viewModel.verified(idOrganization)
        }

        binding.btnNotVerified.setOnClickListener {
            viewModel.dismiss(idOrganization)
        }
    }

    private fun setupListener() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.isVisible = true
                binding.content.isVisible = false
            }
            else {
                binding.progressBar.isVisible = false
                binding.content.isVisible = true
            }
        }

        viewModel.organization.observe(viewLifecycleOwner) { organization ->
            if (organization != null) {
                binding.progressBar.isVisible = false
                binding.content.isVisible = true

                binding.legalName.text = organization.legalName
                binding.legalAddress.text = organization.legalAddress
                binding.inn.text = organization.inn
                binding.mailOrg.text = organization.email
                binding.cityOrg.text = organization.city
                binding.legalAddress.text = organization.legalAddress
                binding.displayName.text = organization.displayName
                binding.managerPhone.text = organization.managerPhone
                binding.phoneOrg.text = organization.phoneOrg
                binding.imgDocument.load(organization.documentUrl)
            }
        }

        viewModel.isNavigate.observe(viewLifecycleOwner) { isNavigate ->
            if (isNavigate) {
                findNavController().popBackStack()
            }
        }
    }
}