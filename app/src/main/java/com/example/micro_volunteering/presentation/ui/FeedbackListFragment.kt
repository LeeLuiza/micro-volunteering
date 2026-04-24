package com.example.micro_volunteering.presentation.ui

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.micro_volunteering.databinding.FragmentFeedbackListBinding
import com.example.micro_volunteering.presentation.adapter.FeedbackAdapter
import com.example.micro_volunteering.presentation.viewmodel.FeedbackListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbackListFragment : BaseFragment<FragmentFeedbackListBinding, FeedbackListViewModel>(FragmentFeedbackListBinding::inflate) {

    override val viewModel: FeedbackListViewModel by viewModels()
    private lateinit var adapter: FeedbackAdapter

    override fun setupViews() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = FeedbackAdapter()
        binding.recyclerViewFeedback.adapter = adapter
    }

    override fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.recyclerViewFeedback.isVisible = !isLoading
        }

        viewModel.feedbacks.observe(viewLifecycleOwner) { feedbacks ->
            adapter.updateFeedbacks(feedbacks)
        }
    }
}