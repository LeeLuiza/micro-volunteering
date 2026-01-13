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
import com.example.micro_volunteering.databinding.FragmentUpdateTaskBinding
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

        observeViewModel()
        setData()

        binding.btnSave.setOnClickListener {
            viewModel.updateTask(
                args.task.id,
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
                val action = UpdateTaskFragmentDirections.actionUpdateTaskFragmentToTaskFragment(taskId)
                findNavController().navigate(action)
            }
        }
    }

    fun setData() {
        binding.titleEdit.setText(args.task.title)
        binding.descriptionEdit.setText(args.task.description)
        binding.addressEdit.setText(args.task.address)
        binding.category.setText(args.task.category)
        binding.volNeeded.setText(args.task.volunteersNeeded.toString())
    }
}