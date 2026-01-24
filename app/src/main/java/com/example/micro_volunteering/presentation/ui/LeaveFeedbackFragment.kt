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
import com.example.micro_volunteering.databinding.FragmentLeaveFeedbackBinding
import com.example.micro_volunteering.presentation.viewmodel.LeaveFeedbackViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class LeaveFeedbackFragment : Fragment() {

    private lateinit var binding: FragmentLeaveFeedbackBinding
    private val viewModel: LeaveFeedbackViewModel by viewModels()

    private val args: LeaveFeedbackFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLeaveFeedbackBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ratingBar.rating = args.rating
        binding.name.text = args.name
        binding.img.load(args.url)

        binding.btnSave.setOnClickListener {
            viewModel.leaveFeedback(args.idVolunteer, args.idTask, binding.editFeedback.text.toString(), binding.ratingBar.rating)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
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

        viewModel.isSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                findNavController().popBackStack()
            }
        }

        viewModel.errorText.observe(viewLifecycleOwner) { errorText ->
            if (errorText != null) {
                binding.errorText.text = getString(errorText)
                binding.errorText.isVisible = true
            } else {
                binding.errorText.isVisible = false
            }
        }
    }
}