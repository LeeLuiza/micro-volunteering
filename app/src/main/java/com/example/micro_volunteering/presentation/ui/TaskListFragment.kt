package com.example.micro_volunteering.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.micro_volunteering.databinding.FragmentTaskListBinding
import com.example.micro_volunteering.domain.model.TaskStatus
import com.example.micro_volunteering.presentation.adapter.TaskAdapter
import com.example.micro_volunteering.presentation.viewmodel.TaskListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private lateinit var binding: FragmentTaskListBinding
    private val viewModel: TaskListViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.setNotificationPermissionRequested()
    }

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
        viewModel.checkPermissionStatus()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnNewTask.setOnClickListener {
            val action = TaskListFragmentDirections.actionTaskListFragmentToCreateTaskFragment()
            findNavController().navigate(action)
        }

        binding.btnActiveTasks.setOnClickListener {
            viewModel.changeTab(TaskStatus.ACTIVE)
        }

        binding.btnCompletedTasks.setOnClickListener {
            viewModel.changeTab(TaskStatus.COMPLETE)
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
                binding.tabContainer.isVisible = viewModel.isUserOrganization()
            }
        }

        viewModel.isNotificationPermissionRequested.observe(viewLifecycleOwner) { isNotification ->
            if (isNotification) {
                notificationPermissionRequested()
            }
        }

        viewModel.selectedTab.observe(viewLifecycleOwner) { status ->
            updateTab(status)
        }
    }

    private fun notificationPermissionRequested() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasPermission) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                viewModel.setNotificationPermissionRequested()
            }
        }
    }

    private fun updateTab(status: TaskStatus) {
        if (status == TaskStatus.ACTIVE) {
            binding.btnActiveTasks.isSelected = true
        }
        else {
            binding.btnCompletedTasks.isSelected = false
        }
    }
}