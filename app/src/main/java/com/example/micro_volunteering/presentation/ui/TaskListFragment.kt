package com.example.micro_volunteering.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentTaskListBinding
import com.example.micro_volunteering.domain.model.CategoryTask
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
        setupCategorySpinner()
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

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchTasks(newText)
                return true
            }

        })

        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var selectedCategory: CategoryTask? = null
                if (position != 0) {
                    selectedCategory = CategoryTask.entries[position - 1]
                }
                viewModel.filterTasks(selectedCategory)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModel.filterTasks(null)
            }
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
            val isVerified = viewModel.isVerified()
            val isOrganization = viewModel.isUserOrganization()
            if (tasks.isNotEmpty()) {
                binding.progressBar.isVisible = false
                binding.recyclerViewTasks.isVisible = true
            }
            binding.tabContainer.isVisible = isOrganization

            if (isVerified) {
                binding.textVerified.isVisible = false
                binding.btnNewTask.isVisible = isOrganization
            }
            else {
                binding.textVerified.isVisible = isOrganization
                binding.textVerified.text = getString(R.string.text_verified)
            }

            adapter.updateTasks(tasks)
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
        binding.btnActiveTasks.isSelected = (status == TaskStatus.ACTIVE)
        binding.btnCompletedTasks.isSelected = (status == TaskStatus.COMPLETE)
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

        val allCategories = mutableListOf<String>().apply {
            add(getString(R.string.all_categories))
            addAll(categories)
        }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            allCategories
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter
    }
}