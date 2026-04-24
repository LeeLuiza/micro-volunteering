package com.example.micro_volunteering.presentation.ui

import android.app.AlertDialog
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentUpdateUserInfoBinding
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.presentation.utils.navigate
import com.example.micro_volunteering.presentation.viewmodel.UpdateUserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class UpdateUserInfoFragment : BaseFragment<FragmentUpdateUserInfoBinding, UpdateUserInfoViewModel>(
    FragmentUpdateUserInfoBinding::inflate
) {

    override val viewModel: UpdateUserInfoViewModel by viewModels()
    private val args: UpdateUserInfoFragmentArgs by navArgs()

    override fun setupViews() {
        val user = args.user

        setupListeners(user)

        binding.editInfoVolunteer.isVisible = user is UserProfile.Volunteer
        binding.editInfoOrganization.isVisible = user is UserProfile.Organization

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
            showDeleteDialog()
        }
    }

    override fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.editInfoOrganization.isVisible = isLoading
            binding.editInfoVolunteer.isVisible = isLoading
            binding.progressBar.isVisible = !isLoading
        }

        viewModel.updateSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                navigate(UpdateUserInfoFragmentDirections.actionUpdateUserInfoFragmentToUserInfoFragment())
            }
        }

        viewModel.deleteSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                navigate(UpdateUserInfoFragmentDirections.actionUpdateUserInfoFragmentToRoleSelectionFragment())
            }
        }
    }

    private fun setupVolunteerUI(user: UserProfile.Volunteer) {
        with(binding) {
            fullName.setText(user.name)
            mail.setText(user.email)
            city.setText(user.city)
            age.setText(user.age.toString())
            phone.setText(user.phone.toString())
        }
    }

    private fun setupOrganizationUI(user: UserProfile.Organization) {
        with(binding) {
            legalName.setText(user.legalName)
            inn.setText(user.inn)
            mailOrg.setText(user.email)
            cityOrg.setText(user.city)
            legalAddress.setText(user.legalAddress)
            displayName.setText(user.displayName)
            managerPhone.setText(user.managerPhone)
            phoneOrg.setText(user.phoneOrg)
        }
    }

    private fun saveChange(user: UserProfile) {
        with(binding) {
            when (user) {
                is UserProfile.Volunteer -> viewModel.updateVolunteer(
                    name = fullName.text.toString(),
                    phone = phone.text.toString(),
                    email = mail.text.toString(),
                    ageRaw = age.text.toString(),
                    city = city.text.toString()
                )
                is UserProfile.Organization -> viewModel.updateOrganization(
                    legalName = legalName.text.toString(),
                    inn = inn.text.toString(),
                    legalAddress = legalAddress.text.toString(),
                    displayName = displayName.text.toString(),
                    managerPhone = managerPhone.text.toString(),
                    phone = phoneOrg.text.toString(),
                    email = mailOrg.text.toString(),
                    city = cityOrg.text.toString()
                )
            }
        }
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_account_title)
            .setMessage(R.string.delete_account_message)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                viewModel.deleteAccount()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}