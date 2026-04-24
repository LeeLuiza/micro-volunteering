package com.example.micro_volunteering.presentation.ui

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.micro_volunteering.databinding.FragmentLeaveFeedbackBinding
import com.example.micro_volunteering.presentation.viewmodel.LeaveFeedbackViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class LeaveFeedbackFragment : BaseFragment<FragmentLeaveFeedbackBinding, LeaveFeedbackViewModel>(FragmentLeaveFeedbackBinding::inflate) {

    override val viewModel: LeaveFeedbackViewModel by viewModels()
    private val args: LeaveFeedbackFragmentArgs by navArgs()

    override fun setupViews() {
        with (binding) {
            ratingBar.rating = args.rating
            name.text = args.name
            img.load(args.url)
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            viewModel.leaveFeedback(
                args.idVolunteer,
                args.idTask,
                binding.editFeedback.text.toString(),
                binding.ratingBar.rating
            )
        }
    }

    override fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.content.isVisible = !isLoading
        }

        viewModel.isSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                findNavController().popBackStack()
            }
        }

        viewModel.errorText.observe(viewLifecycleOwner) { errorText ->
            errorText?.let {
                binding.errorText.text = errorText
            }
            binding.errorText.isVisible = !errorText.isNullOrEmpty()
        }
    }
}