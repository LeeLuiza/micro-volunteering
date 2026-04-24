package com.example.micro_volunteering.presentation.ui

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentTaskBinding
import com.example.micro_volunteering.presentation.utils.getDisplayName
import com.example.micro_volunteering.presentation.utils.navigate
import com.example.micro_volunteering.presentation.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class TaskFragment : BaseFragment<FragmentTaskBinding, TaskViewModel>(FragmentTaskBinding::inflate) {

    override val viewModel: TaskViewModel by viewModels()
    private val args: TaskFragmentArgs by navArgs()

    override fun setupViews() {
        setupListeners()
    }

    private fun setupListeners() {
        val currentId = args.id

        binding.btnCorrect.setOnClickListener {
            viewModel.task.value?.let { currentTask ->
                navigate(TaskFragmentDirections.actionTaskFragmentToUpdateTaskFragment(currentTask))
            }
        }

        binding.btnComplete.setOnClickListener {
            viewModel.completeTask(currentId)
        }

        binding.btnViewParticipants.setOnClickListener {
            navigate(TaskFragmentDirections.actionTaskFragmentToVolunteerRespondFragment(currentId))
        }

        binding.btnRespond.setOnClickListener {
            viewModel.respond(currentId)
        }
    }

    override fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.content.isVisible = !isLoading
        }

        viewModel.isComplete.observe(viewLifecycleOwner) { isComplete ->
            if (isComplete) {
                findNavController().popBackStack()
            }
        }

        viewModel.task.observe(viewLifecycleOwner) { task ->
            task?.let {
                with(binding) {
                    progressBar.isVisible = false
                    content.isVisible = true

                    title.text = task.title
                    description.text = task.description
                    category.text = task.category.getDisplayName(requireContext())
                    countVol.text = getString(R.string.number_volunteers, task.volunteersNeeded.toString())
                    address.text = task.address
                    date.text = task.date
                    organization.text = task.organizationName
                    btnCorrect.isVisible = viewModel.isUserOrganization()
                    btnViewParticipants.isVisible = viewModel.isUserOrganization()
                    btnComplete.isVisible = viewModel.isUserOrganization()
                    btnRespond.isVisible = !viewModel.isUserOrganization()
                }
            }
        }

        viewModel.isRespond.observe(viewLifecycleOwner) { isRespond ->
            if (isRespond) {
                Toast.makeText(requireContext(), R.string.successfully_responded, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun loadData() {
        viewModel.loadTask(args.id)
    }
}