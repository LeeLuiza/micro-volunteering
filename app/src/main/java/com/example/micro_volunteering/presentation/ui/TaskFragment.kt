package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentTaskBinding
import com.example.micro_volunteering.domain.model.CategoryTask
import com.example.micro_volunteering.presentation.extensions.navigate
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

        val idTask = args.id

        observeViewModel()
        loadTasks(idTask)
        setupListeners(idTask)
    }

    private fun setupListeners(idTask: Int) {

        binding.btnCorrect.setOnClickListener {
            val currentTask = viewModel.tasks.value

            if (currentTask != null) {
                val action = TaskFragmentDirections.actionTaskFragmentToUpdateTaskFragment(currentTask)
                navigate(action)
            }
        }

        binding.btnComplete.setOnClickListener {
            viewModel.completeTask(idTask)
        }

        binding.btnViewParticipants.setOnClickListener {
            val action = TaskFragmentDirections.actionTaskFragmentToVolunteerRespondFragment(idTask)
            navigate(action)
        }

        binding.btnRespond.setOnClickListener {
            viewModel.respond(idTask)
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.isVisible = true
                binding.content.isVisible = false
            }
            else {
                binding.progressBar.isVisible = false
                binding.content.isVisible = true
            }
        }

        viewModel.isComplete.observe(viewLifecycleOwner) { isComplete ->
            if (isComplete) {
                findNavController().popBackStack()
            }
        }

        viewModel.tasks.observe(viewLifecycleOwner) { task ->
            if (task != null) {
                binding.progressBar.isVisible = false
                binding.content.isVisible = true

                binding.title.text = task.title
                binding.description.text = task.description
                binding.category.text = getCategoryName(task.category)
                binding.countVol.text = getString(R.string.number_volunteers, task.volunteersNeeded.toString())
                binding.address.text = task.address
                binding.date.text = task.date
                binding.organization.text = task.organizationName
                binding.btnCorrect.isVisible = viewModel.isUserOrganization()
                binding.btnViewParticipants.isVisible = viewModel.isUserOrganization()
                binding.btnComplete.isVisible = viewModel.isUserOrganization()
                binding.btnRespond.isVisible = !viewModel.isUserOrganization()
            }
        }

        viewModel.isRespond.observe(viewLifecycleOwner) { isRespond ->
            if (isRespond) {
                Toast.makeText(requireContext(), R.string.successfully_responded, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCategoryName(category: CategoryTask): String {
        val resId = when (category) {
            CategoryTask.ECOLOGY -> R.string.ecology
            CategoryTask.ANIMAL -> R.string.animal
            CategoryTask.SOCIAL_ASSIST -> R.string.social_assist
            CategoryTask.CAR -> R.string.car
            CategoryTask.MENTAL -> R.string.mental
            CategoryTask.EVENT -> R.string.event
            CategoryTask.OTHER -> R.string.other
        }
        return getString(resId)
    }

    private fun loadTasks(idTask: Int) {
        viewModel.loadTasks(idTask)
    }
}