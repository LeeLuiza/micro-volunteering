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
import com.example.micro_volunteering.databinding.FragmentUserInfoBinding
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.presentation.viewmodel.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoFragment : Fragment() {

    private lateinit var binding: FragmentUserInfoBinding
    private val viewModel: UserInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        viewModel.loadUserInfo()

        binding.btnCorrect.setOnClickListener {
            val currentUser = viewModel.profile.value

            if (currentUser != null) {
                val action = UserInfoFragmentDirections.actionUserInfoFragmentToUpdateUserInfoFragment(currentUser)
                findNavController().navigate(action)
            }
        }

        binding.btnCorrectOrg.setOnClickListener {
            val currentUser = viewModel.profile.value

            if (currentUser != null) {
                val action = UserInfoFragmentDirections.actionUserInfoFragmentToUpdateUserInfoFragment(currentUser)
                findNavController().navigate(action)
            }
        }

        binding.btnLogoutOrg.setOnClickListener {
            viewModel.logout()
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.isVisible = true
                binding.contentVolunteerInfo.isVisible = false
                binding.contentOrganizationInfo.isVisible = false
            }
            else {
                binding.progressBar.isVisible = false
            }
        }

        viewModel.profile.observe(viewLifecycleOwner) { profile ->
            if (profile != null) {
                when (profile) {
                    is UserProfile.Volunteer -> {
                        binding.contentVolunteerInfo.isVisible = true
                        binding.contentOrganizationInfo.isVisible = false

                        binding.fullName.text = profile.name
                        binding.mail.text = getString(R.string.mail, profile.email)
                        binding.city.text = getString(R.string.city_profile, profile.city)
                        binding.phone.text = getString(R.string.number_profile, profile.phone)
                        binding.age.text = getString(R.string.age_profile, profile.age.toString())
                    }
                    is UserProfile.Organization -> {
                        binding.contentVolunteerInfo.isVisible = false
                        binding.contentOrganizationInfo.isVisible = true

                        binding.legalName.text = profile.legalName
                        binding.inn.text = getString(R.string.inn_profile, profile.inn)
                        binding.mailOrg.text = getString(R.string.mail, profile.email)
                        binding.cityOrg.text = getString(R.string.city_profile, profile.city)
                        binding.legalAddress.text = getString(R.string.address_profile, profile.legalAddress)
                        binding.displayName.text = getString(R.string.name_profile, profile.displayName)
                        binding.managerPhone.text = getString(R.string.manager_phone_profile, profile.managerPhone)
                        binding.phoneOrg.text = getString(R.string.phone_organization, profile.phoneOrg)
                        binding.isVerified.isVisible = profile.isVerified

                        if (profile.isVerified) {
                            binding.isVerified.setText(R.string.verified)
                        }
                    }
                }
            }
        }

        viewModel.logoutSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                val action = UserInfoFragmentDirections.actionUserInfoFragmentToRoleSelectionFragment()
                findNavController().navigate(action)
            }
        }
    }
}