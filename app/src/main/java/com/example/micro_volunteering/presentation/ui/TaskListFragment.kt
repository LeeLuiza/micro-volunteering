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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentTaskListBinding
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.presentation.adapter.TaskAdapter
import com.example.micro_volunteering.presentation.viewmodel.TaskListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private lateinit var binding: FragmentTaskListBinding
    private val viewModel: TaskListViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        viewModel.loadTasks()

        binding.btnNewTask.setOnClickListener {
            val action = TaskListFragmentDirections.actionTaskListFragmentToCreateTaskFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(emptyList()) { id ->
            val action = TaskListFragmentDirections.actionTaskListFragmentToTaskFragment(id)
            findNavController().navigate(action)
        }

        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewTasks.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.isVisible = true
                binding.recyclerViewTasks.isVisible = false
            }
            else {
                binding.progressBar.isVisible = false
                binding.recyclerViewTasks.isVisible = true
            }
        }

        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            if (tasks.isNotEmpty()) {
                binding.progressBar.isVisible = false
                binding.recyclerViewTasks.isVisible = true
                adapter.updateTasks(tasks)
                binding.btnNewTask.isVisible = viewModel.isUserOrganization()
            }
        }
    }
}