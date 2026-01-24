package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentNotificationBinding
import com.example.micro_volunteering.presentation.adapter.NotificationAdapter
import com.example.micro_volunteering.presentation.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private val viewModel: NotificationViewModel by viewModels()

    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadNotification()
        observeViewModel()
        setupRecyclerView()
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

        viewModel.notification.observe(viewLifecycleOwner) { notification ->
            if (!notification.isEmpty()) {
                binding.progressBar.isVisible = false
                binding.content.isVisible = true
            }
            adapter.updateNotification(notification)
        }

        viewModel.isAccept.observe(viewLifecycleOwner) { isAccept ->
            if (isAccept){
                Toast.makeText(requireContext(), R.string.accepted_user, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isDismiss.observe(viewLifecycleOwner) { isDismiss ->
            if (isDismiss){
                Toast.makeText(requireContext(), R.string.dismiss_user, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        val isOrganization = viewModel.isUserOrganization()

        adapter = NotificationAdapter(
            notifications = emptyList(),
            isOrganization = isOrganization,
            onItemClick = { userId ->

            },
            onAcceptClick = { idTask, idVolunteer ->
                viewModel.acceptVolunteer(idTask, idVolunteer)
            },
            onDismissClick = { idTask, idVolunteer ->
                viewModel.dismissVolunteer(idTask, idVolunteer)
            }
        )

        binding.recyclerViewNotification.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewNotification.adapter = adapter
    }
}