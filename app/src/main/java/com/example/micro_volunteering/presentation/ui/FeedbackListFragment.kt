package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.micro_volunteering.databinding.FragmentFeedbackListBinding
import com.example.micro_volunteering.presentation.adapter.FeedbackAdapter
import com.example.micro_volunteering.presentation.viewmodel.FeedbackListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbackListFragment : Fragment() {

    private lateinit var binding: FragmentFeedbackListBinding
    private val viewModel: FeedbackListViewModel by viewModels()

    private lateinit var adapter: FeedbackAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedbackListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.loadFeedback()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.isVisible = true
                binding.recyclerViewFeedback.isVisible = false
            }
        }

        viewModel.feedbacks.observe(viewLifecycleOwner) { feedbacks ->
            if (feedbacks.isNotEmpty()) {
                binding.progressBar.isVisible = false
                binding.recyclerViewFeedback.isVisible = true
                adapter.updateFeedbacks(feedbacks)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = FeedbackAdapter(emptyList())
        binding.recyclerViewFeedback.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFeedback.adapter = adapter
    }
}