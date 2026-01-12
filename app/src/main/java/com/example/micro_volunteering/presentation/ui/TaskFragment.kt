package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentTaskBinding
import com.example.micro_volunteering.presentation.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    private val viewModel: TaskViewModel by viewModels()

    private val args: TaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        loadTasks()

        binding.btnCorrect.setOnClickListener {
            val currentTask = viewModel.tasks.value

            if (currentTask != null) {
                val action = TaskFragmentDirections.actionTaskFragmentToUpdateTaskFragment(currentTask)
                findNavController().navigate(action)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.isVisible = true
                binding.content.isVisible = false
            }
        }

        viewModel.tasks.observe(viewLifecycleOwner) { task ->
            if (task != null) {
                binding.progressBar.isVisible = false
                binding.content.isVisible = true

                binding.title.text = task.title
                binding.description.text = task.description
                binding.category.text = task.category
                binding.countVol.text = getString(R.string.number_volunteers, task.volunteersNeeded.toString())
                binding.address.text = task.address
                binding.date.text = task.date
                binding.organization.text = task.organizationName
                binding.btnCorrect.isVisible = viewModel.isUserOrganization()
                binding.btnRespond.isVisible = !viewModel.isUserOrganization()
            }
        }
    }

    private fun loadTasks() {
        viewModel.loadTasks(args.id)
    }
}