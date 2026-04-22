package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.micro_volunteering.databinding.FragmentAdminBinding
import com.example.micro_volunteering.presentation.adapter.OrganizationAdapter
import com.example.micro_volunteering.presentation.extensions.navigate
import com.example.micro_volunteering.presentation.viewmodel.AdminPanelViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminPanelFragment : Fragment() {

    private lateinit var binding: FragmentAdminBinding
    private val viewModel: AdminPanelViewModel by viewModels()

    private lateinit var adapter: OrganizationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadUnverifiedOrganizationList()
        setupRecyclerView()
        observeViewModel()
        setupListener()
    }

    private fun setupListener() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.isVisible = true
                binding.recyclerViewOrganization.isVisible = false
            }
            else {
                binding.progressBar.isVisible = false
                binding.recyclerViewOrganization.isVisible = true
            }
        }

        viewModel.unverifiedOrganization.observe(viewLifecycleOwner) { unverifiedOrganization ->
            if (unverifiedOrganization.isNotEmpty()) {
                binding.progressBar.isVisible = false
                binding.recyclerViewOrganization.isVisible = true
            }

            adapter.updateOrganizations(unverifiedOrganization)
        }

        viewModel.logoutSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                val action = AdminPanelFragmentDirections.actionAdminPanelFragmentToRoleSelectionFragment()
                navigate(action)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = OrganizationAdapter { id ->
            val action = AdminPanelFragmentDirections.actionAdminPanelFragmentToUnverifiedOrganizationFragment(id)
            navigate(action)
        }
        binding.recyclerViewOrganization.adapter = adapter
    }
}