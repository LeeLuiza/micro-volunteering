package com.example.micro_volunteering.presentation.ui

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.micro_volunteering.databinding.FragmentVolunteerRespondBinding
import com.example.micro_volunteering.presentation.adapter.RespondersAdapter
import com.example.micro_volunteering.presentation.utils.navigate
import com.example.micro_volunteering.presentation.viewmodel.TaskRespondersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class TaskRespondersFragment : BaseFragment<FragmentVolunteerRespondBinding, TaskRespondersViewModel>(
    FragmentVolunteerRespondBinding::inflate
) {

    override val viewModel: TaskRespondersViewModel by viewModels()
    private val args: VolunteerRespondFragmentArgs by navArgs()
    private lateinit var adapter: RespondersAdapter

    override fun setupViews() {
        setUpRecyclerView()
    }

    override fun observeViewModel() {
        collectFlow(viewModel.isLoading) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.recyclerViewVolunteer.isVisible = !isLoading
        }

        collectFlow(viewModel.volunteerResponders) { volunteerRespond ->
            binding.progressBar.isVisible = false
            binding.recyclerViewVolunteer.isVisible = volunteerRespond.isNotEmpty()
            adapter.update(volunteerRespond)
        }
    }

    private fun setUpRecyclerView() {
        adapter = RespondersAdapter { idVolunteer, rating, name, url ->
            val action = VolunteerRespondFragmentDirections.actionVolunteerRespondFragmentToLeaveFeedbackFragment(
                idVolunteer, args.id, name, url, rating
            )
            navigate(action)
        }
        binding.recyclerViewVolunteer.adapter = adapter
    }

    override fun loadData() {
        viewModel.loadResponders(args.id)
    }
}