package com.example.micro_volunteering.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentTaskListBinding
import com.example.micro_volunteering.domain.model.CategoryTask
import com.example.micro_volunteering.domain.model.TaskStatus
import com.example.micro_volunteering.presentation.adapter.TaskAdapter
import com.example.micro_volunteering.presentation.utils.getDisplayName
import com.example.micro_volunteering.presentation.utils.navigate
import com.example.micro_volunteering.presentation.viewmodel.TaskListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class TasksFragment : BaseFragment<FragmentTaskListBinding, TaskListViewModel>(FragmentTaskListBinding::inflate) {

    override val viewModel: TaskListViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.setNotificationPermissionRequested()
    }

    override fun setupViews() {
        setupRecyclerView()
        setupListeners()
        setupCategorySpinner()
    }

    private fun setupListeners() {
        binding.btnNewTask.setOnClickListener {
            navigate(TasksFragmentDirections.actionTaskListFragmentToCreateTaskFragment())
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
        adapter = TaskAdapter { id ->
            navigate(TasksFragmentDirections.actionTaskListFragmentToTaskFragment(id))
        }
        binding.recyclerViewTasks.adapter = adapter
    }

    override fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            val isVerified = viewModel.isVerified()
            val isOrganization = viewModel.isUserOrganization()

            with(binding) {
                tabContainer.isVisible = isOrganization
                btnNewTask.isVisible = isOrganization && isVerified
                textVerified.isVisible = isOrganization && !isVerified
                recyclerViewTasks.isVisible = tasks.isNotEmpty()

                if (isOrganization && !isVerified) {
                    textVerified.text = getString(R.string.text_verified)
                }
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
            category.getDisplayName(requireContext())
        }

        val allCategories = mutableListOf<String>().apply {
            add(getString(R.string.all_categories))
            addAll(categories)
        }

        binding.spinnerCategory.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            allCategories
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }
}