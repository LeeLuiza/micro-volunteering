package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.micro_volunteering.databinding.FragmentCreateTaskBinding
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

        binding.btnSave.setOnClickListener {
            viewModel.createTask(
                binding.titleEdit.text.toString(),
                binding.descriptionEdit.text.toString(),
                binding.addressEdit.text.toString(),
                binding.category.text.toString(),
                binding.volNeeded.text.toString().toIntOrNull() ?: 0
            )
        }
    }

    fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.isVisible = true
                binding.content.isVisible = false
            }
        }

        viewModel.taskId.observe(viewLifecycleOwner) { taskId ->
            if (taskId != null) {
                val action = CreateTaskFragmentDirections.actionCreateTaskFragmentToTaskFragment(taskId)
                findNavController().navigate(action)
            }
        }
    }
}