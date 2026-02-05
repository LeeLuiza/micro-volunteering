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
import com.example.micro_volunteering.databinding.FragmentUpdateUserInfoBinding
import com.example.micro_volunteering.domain.model.UpdateProfileParams
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.model.UserProfileRegister
import com.example.micro_volunteering.presentation.viewmodel.UpdateUserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class UpdateUserInfoFragment : Fragment() {

    private lateinit var binding: FragmentUpdateUserInfoBinding
    private val viewModel: UpdateUserInfoViewModel by viewModels()

    private val args: UpdateUserInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateUserInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = args.user

        observeViewModel()
        setupListeners(user)

        when (user) {
            is UserProfile.Volunteer -> setupVolunteerUI(user)
            is UserProfile.Organization -> setupOrganizationUI(user)
        }
    }

    private fun setupListeners(user: UserProfile) {
        binding.btnSave.setOnClickListener {
            saveChange(user)
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteAccount()
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.editInfoOrganization.isVisible = false
                binding.editInfoVolunteer.isVisible = false
                binding.progressBar.isVisible = true
            }
        }

        viewModel.updateSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                val action = UpdateUserInfoFragmentDirections.actionUpdateUserInfoFragmentToUserInfoFragment()
                findNavController().navigate(action)
            }
        }

        viewModel.deleteSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                val action = UpdateUserInfoFragmentDirections.actionUpdateUserInfoFragmentToRoleSelectionFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun setupVolunteerUI(user: UserProfile.Volunteer) {
        binding.editInfoVolunteer.isVisible = true
        binding.editInfoOrganization.isVisible = false

        binding.fullName.setText(user.name)
        binding.mail.setText(user.email)
        binding.city.setText(user.city)
        binding.age.setText(user.age.toString())
        binding.phone.setText(user.phone.toString())
    }

    private fun setupOrganizationUI(user: UserProfile.Organization) {
        binding.editInfoVolunteer.isVisible = false
        binding.editInfoOrganization.isVisible = true

        binding.legalName.setText(user.legalName)
        binding.inn.setText(user.inn)
        binding.mailOrg.setText(user.email)
        binding.cityOrg.setText(user.city)
        binding.legalAddress.setText(user.legalAddress)
        binding.displayName.setText(user.displayName)
        binding.managerPhone.setText(user.managerPhone)
        binding.phoneOrg.setText(user.phoneOrg)
    }

    private fun saveChange(user: UserProfile) {
        val user = when (user) {
            is UserProfile.Volunteer -> UpdateProfileParams.Volunteer(
                binding.fullName.text.toString(),
                binding.phone.text.toString(),
                binding.mail.text.toString(),
                binding.age.text.toString().toIntOrNull() ?: 0,
                binding.city.text.toString()
            )
            is UserProfile.Organization -> UpdateProfileParams.Organization(
                binding.legalName.text.toString(),
                binding.inn.text.toString(),
                binding.legalAddress.text.toString(),
                binding.displayName.text.toString(),
                binding.managerPhone.text.toString(),
                binding.phoneOrg.text.toString(),
                binding.mailOrg.text.toString(),
                binding.cityOrg.text.toString(),
            )
        }

        viewModel.updateUserInfo(user)
    }
}