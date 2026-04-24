package com.example.micro_volunteering.presentation.ui

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentUserInfoBinding
import com.example.micro_volunteering.domain.model.UserProfile
import coil.load
import com.example.micro_volunteering.presentation.utils.navigate
import com.example.micro_volunteering.presentation.viewmodel.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoFragment : BaseFragment<FragmentUserInfoBinding, UserInfoViewModel>(
    FragmentUserInfoBinding::inflate
) {

    override val viewModel: UserInfoViewModel by viewModels()

    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.img.load(uri)
            viewModel.uploadImage(uri)
        }
    }

    private val pickDocument = registerForActivityResult(PickVisualMedia()) { uri ->
        uri?.let {
            binding.imgDocument.load(uri)
            viewModel.uploadDocument(uri)
        }
    }

    override fun setupViews() {
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnCorrect.setOnClickListener {
            viewModel.profile.value?.let { user ->
                navigate(UserInfoFragmentDirections.actionUserInfoFragmentToUpdateUserInfoFragment(user))
            }
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }

        binding.reviews.setOnClickListener {
            navigate(UserInfoFragmentDirections.actionUserInfoFragmentToFeedbackListFragment())
        }

        binding.btnAddImg.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        binding.btnUploadDocument.setOnClickListener {
            pickDocument.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
    }

    override fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.profile.observe(viewLifecycleOwner) { profile ->
            profile?.let { profile ->
                with(binding) {
                    when (profile) {
                        is UserProfile.Volunteer -> {
                            contentVolunteerInfo.isVisible = true
                            contentOrganizationInfo.isVisible = false
                            fullName.text = profile.name
                            img.load(profile.avatarUrl)
                            mail.text = getString(R.string.mail, profile.email)
                            city.text = getString(R.string.city_profile, profile.city)
                            phone.text = getString(R.string.number_profile, profile.phone)
                            age.text = getString(R.string.age_profile, profile.age.toString())
                            ratingBar.rating = profile.rating
                            countTask.text = getString(R.string.count_task, profile.countTask.toString())
                            reviews.text = getString(R.string.reviews, profile.countFeedback.toString())
                        }

                        is UserProfile.Organization -> {
                            contentOrganizationInfo.isVisible = true
                            contentVolunteerInfo.isVisible = false
                            legalName.text = profile.legalName
                            img.load(profile.avatarUrl)
                            inn.text = getString(R.string.inn_profile, profile.inn)
                            mailOrg.text = getString(R.string.mail, profile.email)
                            cityOrg.text = getString(R.string.city_profile, profile.city)
                            legalAddress.text = getString(R.string.address_profile, profile.legalAddress)
                            displayName.text = getString(R.string.name_profile, profile.displayName)
                            managerPhone.text = getString(R.string.manager_phone_profile, profile.managerPhone)
                            phoneOrg.text = getString(R.string.phone_organization, profile.phoneOrg)
                            imgDocument.load(profile.documentUrl)

                            if (profile.isVerified) {
                                isVerified.text = getString(R.string.account_verified)
                                isVerified.setTextColor(
                                    ContextCompat.getColor(
                                        root.context,
                                        R.color.brand_primary
                                    )
                                )
                                btnUploadDocument.isVisible = false
                            } else {
                                isVerified.text = getString(R.string.account_not_verified)
                                isVerified.setTextColor(
                                    ContextCompat.getColor(
                                        root.context,
                                        R.color.error
                                    )
                                )
                                btnUploadDocument.isVisible = true
                            }
                        }
                    }
                }
            }
        }

        viewModel.logoutSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                navigate(UserInfoFragmentDirections.actionUserInfoFragmentToRoleSelectionFragment())
            }
        }
    }
}