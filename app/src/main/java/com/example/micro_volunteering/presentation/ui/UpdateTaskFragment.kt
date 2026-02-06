package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentUpdateTaskBinding
import com.example.micro_volunteering.domain.model.CategoryTask
import com.example.micro_volunteering.presentation.viewmodel.UpdateTaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class UpdateTaskFragment : Fragment() {

    private lateinit var binding: FragmentUpdateTaskBinding
    private  val viewModel: UpdateTaskViewModel by viewModels()

    private val args: UpdateTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idTask = args.task.id

        observeViewModel()
        setData()
        setupListeners(idTask)
        setupCategorySpinner()
    }

    private fun setupListeners(idTask: Int) {
        binding.btnSave.setOnClickListener {
            viewModel.updateTask(
                idTask,
                binding.titleEdit.text.toString(),
                binding.descriptionEdit.text.toString(),
                binding.addressEdit.text.toString(),
                binding.spinnerCategory.selectedItemPosition,
                binding.volNeeded.text.toString()
            )
        }

        binding.btnDeleteTask.setOnClickListener {
            viewModel.deleteTask(idTask)
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
                val action = UpdateTaskFragmentDirections.actionUpdateTaskFragmentToTaskFragment(taskId)
                findNavController().navigate(action)
            }
        }

        viewModel.isSuccessDeleteTask.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                val action = UpdateTaskFragmentDirections.actionUpdateTaskFragmentToTaskListFragment()
                findNavController().navigate(action)
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

    fun setData() {
        val position = CategoryTask.entries.indexOf(args.task.category)

        binding.titleEdit.setText(args.task.title)
        binding.descriptionEdit.setText(args.task.description)
        binding.addressEdit.setText(args.task.address)
        binding.spinnerCategory.setSelection(position)
        binding.volNeeded.setText(args.task.volunteersNeeded.toString())
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