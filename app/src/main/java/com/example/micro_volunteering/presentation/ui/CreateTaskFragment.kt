package com.example.micro_volunteering.presentation.ui

import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.micro_volunteering.databinding.FragmentCreateTaskBinding
import com.example.micro_volunteering.domain.model.CategoryTask
import com.example.micro_volunteering.presentation.utils.getDisplayName
import com.example.micro_volunteering.presentation.utils.navigate
import com.example.micro_volunteering.presentation.viewmodel.CreateTaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTaskFragment : BaseFragment<FragmentCreateTaskBinding, CreateTaskViewModel>(
    FragmentCreateTaskBinding::inflate
) {

    override val viewModel: CreateTaskViewModel by viewModels()

    override fun setupViews() {
        setupListeners()
        setupCategorySpinner()
    }

    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            with(binding) {
                viewModel.createTask(
                    titleEdit.text.toString(),
                    descriptionEdit.text.toString(),
                    addressEdit.text.toString(),
                    spinnerCategory.selectedItemPosition,
                    volNeeded.text.toString()
                )
            }
        }
    }

    override fun observeViewModel() {
        collectFlow(viewModel.isLoading) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.content.isVisible = !isLoading
        }

        collectFlow(viewModel.taskId) { taskId ->
            taskId?.let {
                navigate(CreateTaskFragmentDirections.actionCreateTaskFragmentToTaskFragment(taskId))
            }
        }

        collectFlow(viewModel.errorText) { errorText ->
            binding.errorText.text = errorText
            binding.errorText.isVisible = errorText.isNotEmpty()
        }
    }

    private fun setupCategorySpinner() {
        val categories = CategoryTask.entries.map { category ->
            category.getDisplayName(requireContext())
        }

        binding.spinnerCategory.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }
}