package com.example.micro_volunteering.presentation.ui

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.micro_volunteering.databinding.FragmentAdminBinding
import com.example.micro_volunteering.presentation.adapter.OrganizationAdapter
import com.example.micro_volunteering.presentation.utils.navigate
import com.example.micro_volunteering.presentation.viewmodel.AdminPanelViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminPanelFragment : BaseFragment<FragmentAdminBinding, AdminPanelViewModel>(FragmentAdminBinding::inflate) {

    override val viewModel: AdminPanelViewModel by viewModels()
    private lateinit var adapter: OrganizationAdapter

    override fun setupViews() {
        setupRecyclerView()
        setupListener()
    }

    private fun setupListener() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    override fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.recyclerViewOrganization.isVisible = !isLoading
        }

        viewModel.unverifiedOrganizations.observe(viewLifecycleOwner) { unverifiedOrganization ->
            binding.progressBar.isVisible = false
            binding.recyclerViewOrganization.isVisible = true
            adapter.updateOrganizations(unverifiedOrganization)
        }

        viewModel.logoutSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                navigate(AdminPanelFragmentDirections.actionAdminPanelFragmentToRoleSelectionFragment())
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = OrganizationAdapter { id ->
            navigate(AdminPanelFragmentDirections.actionAdminPanelFragmentToUnverifiedOrganizationFragment(id))
        }
        binding.recyclerViewOrganization.adapter = adapter
    }
}