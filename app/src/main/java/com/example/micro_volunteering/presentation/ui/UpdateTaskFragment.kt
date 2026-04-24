package com.example.micro_volunteering.presentation.ui

import android.app.AlertDialog
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentUpdateTaskBinding
import com.example.micro_volunteering.domain.model.CategoryTask
import com.example.micro_volunteering.presentation.utils.getDisplayName
import com.example.micro_volunteering.presentation.utils.navigate
import com.example.micro_volunteering.presentation.viewmodel.UpdateTaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import kotlin.text.isNotEmpty

@AndroidEntryPoint
class UpdateTaskFragment : BaseFragment<FragmentUpdateTaskBinding, UpdateTaskViewModel>(
    FragmentUpdateTaskBinding::inflate
) {

    override val viewModel: UpdateTaskViewModel by viewModels()
    private val args: UpdateTaskFragmentArgs by navArgs()

    override fun setupViews() {
        val idTask = args.task.id

        bindInitialData()
        setupListeners(idTask)
        setupCategorySpinner()
    }

    private fun setupListeners(idTask: Int) {
        binding.btnSave.setOnClickListener {
            with(binding) {
                viewModel.updateTask(
                    idTask,
                    titleEdit.text.toString(),
                    descriptionEdit.text.toString(),
                    addressEdit.text.toString(),
                    spinnerCategory.selectedItemPosition,
                    volNeeded.text.toString()
                )
            }
        }

        binding.btnDeleteTask.setOnClickListener {
            showDeleteDialog(idTask)
        }
    }

    override fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.content.isVisible = !isLoading
        }

        viewModel.taskId.observe(viewLifecycleOwner) { taskId ->
            taskId?.let {
                navigate(UpdateTaskFragmentDirections.actionUpdateTaskFragmentToTaskFragment(taskId))
            }
        }

        viewModel.isSuccessDeleteTask.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                navigate(UpdateTaskFragmentDirections.actionUpdateTaskFragmentToTaskListFragment())
            }
        }

        viewModel.errorText.observe(viewLifecycleOwner) { errorText ->
            binding.errorText.text = errorText
            binding.errorText.isVisible = errorText.isNotEmpty()
        }
    }

    fun bindInitialData() {
        val position = CategoryTask.entries.indexOf(args.task.category)

        with(binding) {
            titleEdit.setText(args.task.title)
            descriptionEdit.setText(args.task.description)
            addressEdit.setText(args.task.address)
            spinnerCategory.setSelection(position)
            volNeeded.setText(args.task.volunteersNeeded.toString())
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

    private fun showDeleteDialog(idTask: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_account_title)
            .setMessage(R.string.delete_account_message)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                viewModel.deleteTask(idTask)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}