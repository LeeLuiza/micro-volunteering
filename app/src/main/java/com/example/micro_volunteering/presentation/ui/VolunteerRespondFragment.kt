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
import com.example.micro_volunteering.databinding.FragmentVolunteerRespondBinding
import com.example.micro_volunteering.presentation.adapter.VolunteerRespondAdapter
import com.example.micro_volunteering.presentation.viewmodel.VolunteerRespondViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class VolunteerRespondFragment : Fragment() {

    private lateinit var binding: FragmentVolunteerRespondBinding
    private val viewModel: VolunteerRespondViewModel by viewModels()
    private val args: VolunteerRespondFragmentArgs by navArgs()

    private lateinit var adapter: VolunteerRespondAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVolunteerRespondBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadVolunteerRespond(args.id)
        setUpRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.isVisible = true
                binding.recyclerViewVolunteer.isVisible = false
            }
            else {
                binding.progressBar.isVisible = false
                binding.recyclerViewVolunteer.isVisible = true
            }
        }

        viewModel.volunteerRespond.observe(viewLifecycleOwner) { volunteerRespond ->
            if (volunteerRespond.isNotEmpty()) {
                binding.progressBar.isVisible = false
                binding.recyclerViewVolunteer.isVisible = true
            }
            adapter.update(volunteerRespond)
        }
    }

    private fun setUpRecyclerView() {
        adapter = VolunteerRespondAdapter(emptyList()) { idVolunteer, rating, name, url ->
            val action = VolunteerRespondFragmentDirections.actionVolunteerRespondFragmentToLeaveFeedbackFragment(
                idVolunteer, args.id, name, url, rating
            )
            findNavController().navigate(action)
        }

        binding.recyclerViewVolunteer.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewVolunteer.adapter = adapter
    }
}