package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentCreateTaskBinding
import com.example.micro_volunteering.domain.model.CategoryTask
import com.example.micro_volunteering.presentation.extensions.navigate
import com.example.micro_volunteering.presentation.viewmodel.CreateTaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTaskFragment : Fragment() {

    private lateinit var binding: FragmentCreateTaskBinding
    private val viewModel: CreateTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupListeners()
        setupCategorySpinner()
    }

    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            viewModel.createTask(
                binding.titleEdit.text.toString(),
                binding.descriptionEdit.text.toString(),
                binding.addressEdit.text.toString(),
                binding.spinnerCategory.selectedItemPosition,
                binding.volNeeded.text.toString()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.isVisible = true
                binding.content.isVisible = false
            }
        }

        viewModel.taskId.observe(viewLifecycleOwner) { taskId ->
            if (taskId != null) {
                val action = CreateTaskFragmentDirections.actionCreateTaskFragmentToTaskFragment(taskId)
                navigate(action)
            }
        }

        viewModel.errorText.observe(viewLifecycleOwner) { errorText ->
            if (errorText.isNotEmpty()) {
                binding.errorText.text = errorText.joinToString("\n") { id -> "- ${getString(id)}" }
                binding.errorText.isVisible = true
            }
            else {
                binding.errorText.isVisible = false
            }
        }
    }

    private fun setupCategorySpinner() {
        val categories = CategoryTask.entries.map { category ->
            val resId = when (category) {
                CategoryTask.ECOLOGY -> R.string.ecology
                CategoryTask.ANIMAL -> R.string.animal
                CategoryTask.SOCIAL_ASSIST -> R.string.social_assist
                CategoryTask.CAR -> R.string.car
                CategoryTask.MENTAL -> R.string.mental
                CategoryTask.EVENT -> R.string.event
                CategoryTask.OTHER -> R.string.other
            }
            getString(resId)
        }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter
    }
}